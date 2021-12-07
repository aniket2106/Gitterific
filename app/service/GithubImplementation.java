package service;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

public class GithubImplementation implements GithubApi {
    
    private String BASE_URL = "https://api.github.com/";

    private WSClient ws;

    @Inject
    public GithubImplementation(WSClient ws) {
        this.ws = ws;
    }

    @Override
    public CompletionStage<WSResponse> fetchRepos(String searchKey) {
        return ws.url(BASE_URL + "search/repositories")
            .addQueryParameter("sort", "updated")
            .addQueryParameter("order", "desc")
            .addQueryParameter("per_page", "10")
            .addQueryParameter("q", searchKey).get();
    }

}
