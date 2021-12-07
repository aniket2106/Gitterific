package service;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.searchResult.SearchResults;
import play.libs.Json;
import play.libs.ws.WSResponse;

public class GithubService {
    
    @Inject
    private GithubApi githubImplementation;

    private final Logger logger = LoggerFactory.getLogger("play");

    private ObjectMapper mapper;

    public GithubService() {
        mapper = new ObjectMapper();
    }

    public CompletionStage<SearchResults> getRepos(final String searchKeyword) {
        logger.info("Fetching repos from github for keyword: " + searchKeyword);
        return githubImplementation.fetchRepos(searchKeyword)
            .thenApplyAsync(WSResponse -> Json.parse(WSResponse.getBody()))
            .thenApplyAsync(wsResponse -> Json.fromJson(wsResponse, SearchResults.class));
    }

    public CompletionStage<SearchResults> getReposByTopic(final String topic) {
        return githubImplementation.fetchReposByTopic(topic)
            .thenApplyAsync(WSResponse -> Json.parse(WSResponse.getBody()))
            .thenApplyAsync(wsResponse -> Json.fromJson(wsResponse, SearchResults.class));
    }

}
