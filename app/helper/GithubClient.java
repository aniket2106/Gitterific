package helper;

import java.util.List;
import java.util.concurrent.CompletionStage;

import models.searchResult.SearchResults;
import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import play.libs.Json;
import play.libs.ws.WSBodyReadables;
import play.libs.ws.WSBodyWritables;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class makes requests to Github API to fetch content based on parameters.
 */

public class GithubClient implements WSBodyReadables, WSBodyWritables  {
	
	private WSClient wsClient;
	public String BASE_URL="https://api.github.com/";

	private final Logger logger = LoggerFactory.getLogger("play");
	
	public GithubClient() {
		
	}
	public void setWsClient(WSClient wsClient) {
		this.wsClient=wsClient;
	}
	public WSClient getWsClient() {
		return this.wsClient;
	}
	
    /**
     * <p>
     * This method makes request to the <code>search</code> API of Github and
     * fetches the top 10 repos for the <code>searchKey</code> sorted by date of upload.
     * </p>
     *
     * @param searchKey this is the key for which search request is executed
     * @return {@link CompletionStage} of {@link SearchResults}.
     */
	public CompletionStage<SearchResults> fetchRepos(String searchKey){
		WSRequest request = this.wsClient
		.url(BASE_URL + "search/repositories")
		.addQueryParameter("sort", "updated")
		.addQueryParameter("order", "desc")
		.addQueryParameter("per_page", "10")
		.addQueryParameter("q", searchKey);
		
		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, SearchResults.class)).toCompletableFuture();

	}
	
	 /**
     * <p>
     * This method makes request to the <code>repository</code> API of Github and
     * fetches the details for the top 10 repos.
     * </p>
     *
     * @param userName this is the userName for which search request is executed
     * @param repoName this is the repoName for which repo request is executed
     * @return {@link CompletionStage} of {@link RepoDetail}.
     */

	public CompletionStage<RepoDetail> fetchRepoDetail(String userName, String repoName) {
		WSRequest request = this.wsClient
		.url(BASE_URL + "repos/" + userName + "/" + repoName);

		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, RepoDetail.class)).toCompletableFuture();
	}
	
	
	 /**
     * <p>
     * This method makes request to the <code>issues</code> API of Github and
     * fetches the top 10 issues for the repository.
     * </p>
     *
     * @param userName this is the userName for which search request is executed
     * @param repoName this is the repoName for which repo request is executed
     * @return {@link CompletionStage} of {@link IssueItem}.
     */
	public CompletionStage<IssueItem[]> fetchIssues(String userName, String repoName) {
		WSRequest request = this.wsClient
		.url(BASE_URL + "repos/" + userName + "/" + repoName + "/issues")
		.addQueryParameter("per_page", "20");

		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, IssueItem[].class)).toCompletableFuture();
	}
	
	 /**
     * <p>
     * This method makes request to the <code>topic</code> API of Github and
     * fetches the top 10 topics for the repository.
     * </p>
     *
     * @param topic this is the topic for which search request is executed
     * @return {@link CompletionStage} of {@link SearchResults}.
     */
	public CompletionStage<SearchResults> fetchReposByTopic(String topic){
		WSRequest request = this.wsClient
		.url(BASE_URL + "search/repositories")
		.addQueryParameter("sort", "updated")
		.addQueryParameter("order", "desc")
		.addQueryParameter("per_page", "10")
		.addQueryParameter("q", "topic:" + topic);
		
		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, SearchResults.class)).toCompletableFuture();

	}
}
