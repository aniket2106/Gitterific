package repoByTopicActors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
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
 * Actor to handle requests to fetch repos by topic
 */
public class RequestActor extends AbstractActor {
    
    @Inject
    private GithubService githubService;

    private ActorRef userActor;

    private String topic;

    private final Logger logger = LoggerFactory.getLogger("play");

    public static Props props() {
        return Props.create(RequestActor.class, () -> new RequestActor());
    }

    /**
     * Actor class constructor
     */
    public RequestActor() {
        this.userActor = null;
        this.topic = null;
    }

    /**
     * Overriding Receive method to add a message handler for the actor
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TopicActorMessages.TopicRequestActorCreate.class, message -> {
                    logger.info("Registering actor {}", message);
                    userActor = getSender();
                    watchSearchResult(message);
                })
                .build();
    }

    /**
     * Method to watch github api http call and reply the response to requesting actor
     * @param message Immutable message used to create actor
     */
    public CompletionStage<Void> watchSearchResult(TopicActorMessages.TopicRequestActorCreate message) {
        topic = message.topic;
        return githubService.getReposByTopic(topic).thenAcceptAsync(searchResults -> {
            TopicActorMessages.TopicRepoItems repoItem =
                    new TopicActorMessages.TopicRepoItems(searchResults, topic);
          
            userActor.tell(repoItem, self());
        });
    }

    /**
     * Getter for topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Setter for topic
     * @param topic Topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Getter for githubService
     */
    public GithubService getGithubService() {
        return githubService;
    }

    /**
     * Setter for githubService
     * @param githubService Github service
     */
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }
}
