package helper;

import java.util.List;
import java.util.concurrent.CompletionStage;

import models.searchResult.SearchResults;
import models.repoDetails.IssueItem;
import play.libs.Json;
import play.libs.ws.WSBodyReadables;
import play.libs.ws.WSBodyWritables;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public CompletionStage<SearchResults> fetchRepos(String searchKey){
		WSRequest request = this.wsClient
		.url(BASE_URL + "search/repositories")
		.addQueryParameter("sort", "updated")
		.addQueryParameter("order", "desc")
		.addQueryParameter("per_page", "10")
		.addQueryParameter("q", searchKey);
		
		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, SearchResults.class)).toCompletableFuture();

	}

	public CompletionStage<IssueItem[]> fetchIssues(String userName, String repoName) {
		logger.info(userName);
		logger.info(repoName);
		WSRequest request = this.wsClient
		.url(BASE_URL + "repos/" + userName + "/" + repoName + "/issues")
		.addQueryParameter("per_page", "2");

		return request.get().thenApply(wsResponse -> Json.parse(wsResponse.getBody())).thenApply(wsResponse -> Json.fromJson(wsResponse, IssueItem[].class)).toCompletableFuture();
	}

}
