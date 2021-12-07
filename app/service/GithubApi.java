package service;

import java.util.concurrent.CompletionStage;

import play.libs.ws.WSResponse;


public interface GithubApi {

    CompletionStage<WSResponse> fetchRepos(String searchKey);

    CompletionStage<WSResponse> fetchReposByTopic(String topic);

	CompletionStage<WSResponse> fetchRepoDetail(String userName, String repoName);
    
    CompletionStage<WSResponse> fetchIssues(String userName, String repoName);
    
}
