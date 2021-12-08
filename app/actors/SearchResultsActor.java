package actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.searchResult.SearchResultItem;
import models.searchResult.SearchResults;
import scala.concurrent.duration.Duration;
import service.GithubService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * This actor contains a set of searchResults internally that may be used by all websocket clients.

 */
public class SearchResultsActor extends AbstractActorWithTimers {

    @Inject
    private GithubService githubService;

    private ActorRef userActor;

    private String query;

    private final Logger logger = LoggerFactory.getLogger("play");

    private Set<SearchResults> searchResultsItems;

     /**
     * Dummy inner class used for the timer
     */
    public static final class Tick {
    }

    /**
     * Start the timer, create a Tick every 10 seconds
     */
    @Override
    public void preStart() {
        getTimers().startPeriodicTimer("Timer", new Tick(),
                Duration.create(10, TimeUnit.SECONDS));
    }
     /**
     * Constructor
     */
    public SearchResultsActor() {
        this.userActor = null;
        this.query = null;
    }
      /**
     * Handle the incoming messages
     * @return Receive received messages
     */

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.RegisterActor.class, message -> {
                    logger.info("Registering actor {}", message);
                    userActor = sender();
                    getSender().tell("UserActor registered", getSelf());
                })
                .match(Messages.WatchSearchResults.class, message -> {
                    logger.info("Received message WatchSearchResults {}", message);
                    if (message != null && message.query != null && message.query != "") {
                        watchSearchResult(message);
                    }
                })
                .match(Tick.class, message -> {
                    logger.info("Received message Tick {}", message);
                    if (query != null) {
                        tickSearchResults();
                    }
                })
                .build();
    }


    /**
     * watchSearchResult message handling
     * @param message message to handle
     * @return CompletionStage of Void
     */
    public CompletionStage<Void> watchSearchResult(Messages.WatchSearchResults message) {
        // Set the query
        query = message.query;
        return githubService.getRepos(query).thenAcceptAsync(searchResults -> {
            // This is the first time we want to watch a (new) query: reset the list
            this.searchResultsItems = new HashSet<>();
            searchResults.setQuery(query);
            // // Add all the statuses to the list
            searchResultsItems.add(searchResults);
            // items.forEach(item -> item.setQuery(query));
 
            // searchResults.setItems(items);
            
            Messages.RepoItem repoItem =
                    new Messages.RepoItem(searchResultsItems, query);
          
            userActor.tell(repoItem, self());
        });
    }

     /**
     * watchSearchResult message handling
     * @return CompletionStage of void
     */
    public CompletionStage<Void> tickSearchResults() {
        return githubService.getRepos(query).thenAcceptAsync(searchResults -> {
            this.searchResultsItems = new HashSet<>();
            searchResults.setQuery(query);
            searchResultsItems.add(searchResults);
            Messages.RepoItem repoItem = new Messages.RepoItem(searchResultsItems, query);
            userActor.tell(repoItem, self());
        });
    }

     /**
     * Keyword getter
     * @return String query
     */
    public String getQuery() {
        return query;
    }

     /**
     * Setter for the query
     * @param query String query
     */
    public void setQuery(String query) {
        this.query = query;
    }

     /**
     * Setter for the statuses
     * @param statuses Set of Statuses statuses
     */
    public void setStatuses(Set<SearchResults> searchResultsItems) {
        this.searchResultsItems = searchResultsItems;
    }

     /**
     * Statuses getter
     * @return Set of Status statuses
     */
    public Set<SearchResults> getStatuses() {
        return searchResultsItems;
    }
     /**
     * Get GithubService
     * @return GithubService githubService
     */

    public GithubService getGithubService() {
        return githubService;
    }

     /**
     * Set GithubService
     * @param github githubService
     */
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }
}