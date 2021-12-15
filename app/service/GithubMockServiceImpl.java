package service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.searchResult.SearchResults;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

public class GithubMockServiceImpl implements GithubApi {

    private String BASE_URL = "https://api.github.com/";

    private WSClient ws;

    private Logger logger = LoggerFactory.getLogger("play");
    
    
    /**
     * Constructor
     * @param ws WSClient provided by Guice
     */
    @Inject
    public GithubMockServiceImpl(WSClient ws) {
        this.ws = ws;
    }
    
    
    /**
     * Search for Repositories given by a serachKey
     * @param searchKey to search for
     * @return CompletionStage of a WSResponse.
     */
    public CompletionStage<WSResponse> fetchRepos(String searchKey) {
        return ws.url(BASE_URL + "search/repositories")
            .addQueryParameter("sort", "updated")
            .addQueryParameter("order", "desc")
            .addQueryParameter("per_page", "10")
            .addQueryParameter("q", searchKey).get();
    }
    
    
    /**
     * Search for Repositories by topics by a topic string
     * @param topic to search for topics
     * @return CompletionStage of a WSResponse.
     */
    public CompletionStage<SearchResults> fetchReposByTopic(String topic) {
        logger.info("Received request to fetch repos by topic in mock implementation of github API");
        SearchResults searchResults = new SearchResults();
        searchResults.setQuery(topic);
        searchResults.setItems(helper.getDummySearchResults());
        return CompletableFuture.supplyAsync(() -> searchResults);
    }

    /**
     * Fetches Details of a repository by a userName and repoName
     * @param userName to fetch repository 
     * @param repoName to fetch repository
     * @return CompletionStage of a WSResponse.
     */
    public CompletionStage<WSResponse> fetchRepoDetail(String userName, String repoName) {
        return ws.url(BASE_URL + "repos/" + userName + "/" + repoName).get();
    }

    /**
     * Fetches Isuues of a repository by a userName and repoName
     * @param userName to fetch issues 
     * @param repoName to fetch issues
     * @return CompletionStage of a WSResponse.
     */
    public CompletionStage<WSResponse> fetchIssues(String userName, String repoName) {
        return ws.url(BASE_URL + "repos/" + userName + "/" + repoName + "/issues")
		    .addQueryParameter("per_page", "20").get();
    }
    
}
