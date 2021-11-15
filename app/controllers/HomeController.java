package controllers;

import helper.GithubClient;
import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import models.searchResult.GithubInfo;
import models.searchResult.SearchResults;
import play.mvc.*;

import play.libs.ws.WSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	
	@Inject
    WSClient wsClient;

    GithubClient githubClient;

    final Logger logger = LoggerFactory.getLogger("play");
    
    private static List<GithubInfo> githubInfos = new ArrayList(50);
    
    public HomeController() {
        this.githubClient = new GithubClient();
    }

  
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> index(String searchKeyword) 
    {
    	if (searchKeyword == "") {
    		return CompletableFuture.completedFuture(ok(views.html.index.render(githubInfos)));
    	}
    	if (this.githubClient.getWsClient() == null) {
            this.githubClient.setWsClient(wsClient);
        }
        logger.info(searchKeyword);
        CompletionStage<SearchResults> response = this.githubClient.fetchRepos(searchKeyword);
        return response.thenApply(resp -> {
        	GithubInfo githubInfo;
        	githubInfo = new GithubInfo(searchKeyword, resp);
        	githubInfos.add(githubInfo);
        	return ok(views.html.index.render(githubInfos));
        });
    }

    public CompletionStage<Result> issues(String user, String repo) {
        if (this.githubClient.getWsClient() == null) {
            this.githubClient.setWsClient(wsClient);
        }
        CompletionStage<RepoDetail> repoDetailResponse = this.githubClient.fetchRepoDetail(user, repo);
        CompletionStage<IssueItem[]> issueResponse = this.githubClient.fetchIssues(user, repo);

        return repoDetailResponse.thenCombine(issueResponse, (repoDetail, issues) -> {
            repoDetail.setIssueItems(Arrays.asList(issues));
            return ok(views.html.repodetail.render(repoDetail));
        });
    }
}
