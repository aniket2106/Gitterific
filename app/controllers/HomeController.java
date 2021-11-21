package controllers;

import helper.GithubClient;
import helper.Session;
import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;

import models.searchResult.SearchResults;
import play.mvc.*;
import play.api.libs.json.Json;
import play.libs.ws.WSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	
	@Inject
    WSClient wsClient;

    GithubClient githubClient;
    
    final Logger logger = LoggerFactory.getLogger("play");
   

    private static int counter = 1;

    /**
     * Controller Constructor
     */
    public HomeController() {
        this.githubClient = new GithubClient();
    }

  
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> index(Http.Request request, String searchKeyword) 
    {
    	if (searchKeyword == "") {
            if (!Session.isSessionExist(request)) {
                counter += 1;
                return CompletableFuture.completedFuture(ok(views.html.index.render(Session.getSearchResultsHashMapFromSession(request))).addingToSession(request, Session.getSessionKey(), Integer.toString(counter)));
            } else {
                return CompletableFuture.completedFuture(ok(views.html.index.render(Session.getSearchResultsHashMapFromSession(request))));
            }
    	}
    	if (this.githubClient.getWsClient() == null) {
            this.githubClient.setWsClient(wsClient);
        }
        logger.info(searchKeyword);
        
        
        
        
        CompletionStage<SearchResults> response = this.githubClient.fetchRepos(searchKeyword);
        return response.thenApply(resp -> {
        
            Session.setSessionSearchResultsHashMap(request, searchKeyword, resp);
            if (!Session.isSessionExist(request)) {
                counter += 1;
                return ok(views.html.index.render(Session.getSearchResultsHashMapFromSession(request))).addingToSession(request, Session.getSessionKey(), Integer.toString(counter));
            } else {
                return ok(views.html.index.render(Session.getSearchResultsHashMapFromSession(request)));
            }
        });
    }

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

    public CompletionStage<Result> repoByTopic(String topic) {
        if (this.githubClient.getWsClient() == null) {
            this.githubClient.setWsClient(wsClient);
        }
        CompletionStage<SearchResults> reposByTopic = this.githubClient.fetchReposByTopic(topic);
        return reposByTopic.thenApply(repos -> {
            // logger.info(repos.toString());
            return ok(views.html.repoByDetail.render(repos, topic));
        });
    }
}
