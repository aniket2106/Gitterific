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


public class SearchResultsActor extends AbstractActorWithTimers {

    @Inject
    private GithubService githubService;

    private ActorRef userActor;

    private String query;

    private final Logger logger = LoggerFactory.getLogger("play");

    private Set<SearchResults> searchResultsItems;

    public static final class Tick {
    }

    @Override
    public void preStart() {
        getTimers().startPeriodicTimer("Timer", new Tick(),
                Duration.create(10, TimeUnit.SECONDS));
    }

    public SearchResultsActor() {
        this.userActor = null;
        this.query = null;
    }

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

    public CompletionStage<Void> tickSearchResults() {
        return githubService.getRepos(query).thenAcceptAsync(searchResults -> {
            this.searchResultsItems = new HashSet<>();
            searchResults.setQuery(query);
            searchResultsItems.add(searchResults);
            Messages.RepoItem repoItem = new Messages.RepoItem(searchResultsItems, query);
            userActor.tell(repoItem, self());
        });
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setStatuses(Set<SearchResults> searchResultsItems) {
        this.searchResultsItems = searchResultsItems;
    }

    public Set<SearchResults> getStatuses() {
        return searchResultsItems;
    }

    public GithubService getGithubService() {
        return githubService;
    }

    public void setTwitterService(GithubService githubService) {
        this.githubService = githubService;
    }
}