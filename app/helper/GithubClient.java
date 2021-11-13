package helper;

import java.util.concurrent.CompletionStage;

import models.searchResult.SearchResults;
import play.libs.Json;
import play.libs.ws.WSBodyReadables;
import play.libs.ws.WSBodyWritables;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

public class GithubClient implements WSBodyReadables, WSBodyWritables  {
	
	private WSClient wsClient;
	public String BASE_URL="https://api.github.com/";
	
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

}
