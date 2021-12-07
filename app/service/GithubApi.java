package service;

import java.util.concurrent.CompletionStage;

import play.libs.ws.WSResponse;
public interface GithubApi {

    CompletionStage<WSResponse> fetchRepos(String searchKey);

    CompletionStage<WSResponse> fetchReposByTopic(String topic);

	// CompletionStage<JsonNode> fetchRepoDetail(WSClient wsClient, String userName, String repoName);
    
    // CompletionStage<JsonNode> fetchIssues(WSClient wsClient, String userName, String repoName);
    
    // CompletionStage<JsonNode> fetchReposByTopic(WSClient wsClient, String topic);
    
    
}
