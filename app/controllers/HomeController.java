package controllers;

import helper.GithubClient;
import java.util.stream.*;
import helper.Session;
import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import models.*;
import Businesslogic.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import models.searchResult.SearchResults;
import play.mvc.*;
import repoByTopicActors.TopicActorMessages.TopicRepoItems;
import repoByTopicActors.TopicActorMessages.TopicRequestActorCreate;
import repoByTopicActors.RequestActor;
import service.GithubApi;
import play.api.libs.json.Json;
import play.libs.streams.ActorFlow;
import play.libs.ws.WSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.Map;

import static akka.pattern.Patterns.ask;
import scala.compat.java8.FutureConverters;

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 * <p>
 *
 * </p>
 * 
 * @author Group Task
 */
public class HomeController extends Controller {

	@Inject
	WSClient wsClient;

	GithubClient githubClient;
	userProfile profile = new userProfile();
	userRepo userRepo = new userRepo();
	final Logger logger = LoggerFactory.getLogger("play");

	private final ActorRef requestActor;

	/**
	 * Controller Constructor
	 */
	@Inject
	public HomeController(@Named("requestActor") ActorRef requestActor) {
		this.requestActor = requestActor;
		this.githubClient = new GithubClient();
	}

    public Result index(Http.Request request, String searchKeyword) {
            return ok(views.html.index.render(request));
    }

	/**
	 * <p>
	 * With this function all the issues of the hyperlinked reppository can be
	 * fetched.
	 * </p>
	 * 
	 * @param user It represents the github name of the user.
	 * @param repo It represents the name of the repository.
	 * @return It returns list of issues.
	 * @author Dhruvi Modi
	 */
	public CompletionStage<Result> issues(String user, String repo) {
		if (this.githubClient.getWsClient() == null) {
			this.githubClient.setWsClient(wsClient);
		}
		CompletionStage<RepoDetail> repoDetailResponse = this.githubClient.fetchRepoDetail(user, repo);
		CompletionStage<IssueItem[]> issueResponse = this.githubClient.fetchIssues(user, repo);

		return repoDetailResponse.thenCombine(issueResponse, (repoDetail, issues) -> {
			repoDetail.setIssueItems(Arrays.asList(issues));
			return ok(views.html.repodetail.render(repoDetail));
		});
	}

	/**
	 * <p>
	 * With this function all the topics of the repository can be fetched
	 * </p>
	 * 
	 * @param topic It stores the topic.
	 * @return Returns top 10 topics of the repository
	 * @author Keta Thakkar
	 */
	public CompletionStage<Result> repoByTopic(String topic) {

		TopicRequestActorCreate config = new TopicRequestActorCreate(topic);

		return FutureConverters.toJava(ask(requestActor, config, 5000)).thenApply((Object response) -> {
			final TopicRepoItems test = (TopicRepoItems) response;
			return ok(views.html.repoByTopic.render(test.searchResults, topic));
		});
	}

	/**
	 * <p>
	 * This controller is designed to search user's public github information and
	 * repositories
	 * </p>
	 * 
	 * @param username it is used to make an api call to get the user's public
	 *                 github information and repositories
	 * @return html file with user's public github information and repositories
	 * @author Aniket Tailor
	 */
	public CompletionStage<Result> getUserProfile(String username) {
		return CompletableFuture.supplyAsync(() -> profile.getData(username)).thenCombine(
				CompletableFuture.supplyAsync(() -> userRepo.getData(username)),
				(userdata, repo) -> ok(views.html.publicInformation.render(userdata, repo)));
	}
	
	/**
	 * 
	 * @param user It represents the github name of the user.
	 * @param repo It represents the name of the repository.
	 * @return It returns Map of individual word and its count in all issue titles
	 * @author Karansinh Matroja
	 */

	public CompletionStage<Result> stats(String user, String repo) {
		if (this.githubClient.getWsClient() == null) {
			this.githubClient.setWsClient(wsClient);
		}

		String repoSplit[] = repo.split("/");
		String userName = repoSplit[0];
		String repoName = repoSplit[1];

		CompletionStage<RepoDetail> repoDetailResponse = this.githubClient.fetchRepoDetail(userName, repoName);
		CompletionStage<IssueItem[]> issueResponse = this.githubClient.fetchIssues(userName, repoName);

		return repoDetailResponse.thenCombine(issueResponse, (repoDetail, issues) -> {

			// TODo check if issues is not empty

			Stream<IssueItem> issueStream = Stream.of(issues);

			Map<String, Integer> issueMap = issueStream.map(issue -> issue.getTitle().split("\\s+"))
											.flatMap(Arrays::stream)
											.filter(word -> word.length() > 1)
											.collect(Collectors.toMap(i -> i.toLowerCase(), i -> 1, Integer::sum));
			
			issueMap = issueMap.entrySet().stream()
					   .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b,LinkedHashMap::new));

			/*
			 * for (Map.Entry<String, Integer> entry : issueMap.entrySet()) {
			 * System.out.println(entry.getKey() + ":" + entry.getValue()); }
			 */

			return ok(views.html.statistics.render(issueMap));
		});
	}
}