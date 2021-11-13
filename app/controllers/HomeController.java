package controllers;

import helper.GithubClient;
import models.searchResult.SearchResults;
import play.mvc.*;

import play.libs.ws.WSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

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
    
    public HomeController() {
        this.githubClient = new GithubClient();
    }

  
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() 
    {
    	if (this.githubClient.getWsClient() == null) {
            this.githubClient.setWsClient(wsClient);
        }
        logger.info(searchKeyword);
        CompletionStage<SearchResults> response = this.githubClient.fetchRepos(searchKeyword);
        response.thenApply(resp -> {
            logger.info(resp.toString());
            return resp;
        });
        return ok(views.html.index.render());
    }

}
