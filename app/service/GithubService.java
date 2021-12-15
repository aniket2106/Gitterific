package service;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import models.searchResult.SearchResults;
import play.libs.Json;
import play.libs.ws.WSResponse;


/**
 * Fetches data from GithubApi using GithubImplementation and then passes into respective model objects
 * @author dhruvimodi
 */

public class GithubService {
    
    @Inject
    private GithubApi githubImplementation;

    private final Logger logger = LoggerFactory.getLogger("play");

    private ObjectMapper mapper;

    /**
     * Default constructor
     */
    public GithubService() {
        mapper = new ObjectMapper();
        // this.githubImplementation = githubImplementation;
    }

    /**
     * Parse the repositories for a keyword
     * @param searchKeyword keyword
     * @return CompletionStage of a SearchResult
     */
    public CompletionStage<SearchResults> getRepos(final String searchKeyword) {
        logger.info("Fetching repos from github for keyword: " + searchKeyword);
        return githubImplementation.fetchRepos(searchKeyword)
            .thenApplyAsync(WSResponse -> Json.parse(WSResponse.getBody()))
            .thenApplyAsync(wsResponse -> Json.fromJson(wsResponse, SearchResults.class));
    }

    /**
     * Parse the repositories by topic for a keyword
     * @param  keywordtopic
     * @return CompletionStage of a SearchResult
     */
    public CompletionStage<SearchResults> getReposByTopic(final String topic) {
        logger.info("Received request in github service");
        return githubImplementation.fetchReposByTopic(topic);
    }

    /**
     * Parse the repository detsils for a keyword
     * @param userName keyword
     * @param repoName keyword
     * @return CompletionStage of a SearchResult
     */
    public CompletionStage<RepoDetail> getRepoDetails(final String userName, final String repoName) {
        return githubImplementation.fetchRepoDetail(userName, repoName)
            .thenApplyAsync(response -> Json.parse(response.getBody()))
            .thenApplyAsync(response -> Json.fromJson(response, RepoDetail.class));
    }

    /**
     * Parse the issues for a keyword
     * @param userName keyword
     * @param repoName keyword
     * @return CompletionStage of a SearchResult
     */
    public CompletionStage<IssueItem[]> getRepoIssues(final String userName, final String repoName) {
        return githubImplementation.fetchIssues(userName, repoName)
            .thenApplyAsync(response -> Json.parse(response.getBody()))
            .thenApplyAsync(response -> Json.fromJson(response, IssueItem[].class));
    }

}
