package service;

import java.util.concurrent.CompletionStage;

import com.google.inject.ImplementedBy;

import models.searchResult.SearchResults;
import play.libs.ws.WSResponse;

/**	
 * Interface for the GithubApi requests
 * @author dhruvimodi
 *
 */

@ImplementedBy(GithubMockServiceImpl.class)
public interface GithubApi {

    CompletionStage<WSResponse> fetchRepos(String searchKey);

    CompletionStage<SearchResults> fetchReposByTopic(String topic);

	CompletionStage<WSResponse> fetchRepoDetail(String userName, String repoName);
    
    CompletionStage<WSResponse> fetchIssues(String userName, String repoName);
    
}
