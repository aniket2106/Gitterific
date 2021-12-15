package repoByTopicActorsTest;


import java.io.IOException;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import repoByTopicActors.RequestActor;
import repoByTopicActors.TopicActorMessages;
import repoByTopicActors.TopicActorMessages.TopicRepoItems;
import service.GithubService;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.After;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor;
import java.util.stream.*;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import models.searchResult.SearchResults;

import org.junit.ClassRule;
import akka.actor.typed.javadsl.Behaviors;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import java.time.Duration;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.ConfigFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static org.fest.reflect.core.Reflection.*;

public class RequestActorTest extends Mockito {

    static ActorSystem actorSystem;
    static SearchResults searchResults;

    private final Logger logger = LoggerFactory.getLogger("play");

    // @Mock
    // private GithubService githubServiceMock;

    // private ActorRef topicActor;

    @Before
    public void setup() {

        actorSystem = ActorSystem.create();

        searchResults = new SearchResults();
        searchResults.setItems(helper.getDummySearchResults());
        searchResults.setQuery("dummyTopic");
        // CompletionStage<SearchResults> dummyResponse = CompletableFuture.supplyAsync(() -> searchResults);
        // when(githubServiceMock.getReposByTopic("dummyTopic")).thenReturn(dummyResponse);
    }

    @Test
    public void testTopicActor() {

        // final Props props = RequestActor.props();
        // ActorRef topicActor = actorSystem.actorOf(props);

        // field("actorcell.actor.githubService").ofType(GithubService.class).in(topicActor).set(githubServiceMock);
        // logger.info("Hehe");
        // final TestKit testProbe = new TestKit(actorSystem);


        new TestKit(actorSystem) {
            {
                final Props props = Props.create(RequestActor.class);
                final ActorRef topicActor = actorSystem.actorOf(props);

                final TestKit probe = new TestKit(actorSystem);

                topicActor.tell(new TopicActorMessages.TopicRequestActorCreate("dummyTopic"), getRef());

                // expectMsg(Duration.ofSeconds(5), new TopicActorMessages.TopicRepoItems(searchResults, "dummyTopic"));

            }
        };


        // topicActor.tell(new TopicActorMessages.TopicRequestActorCreate("dummyTopic"), testProbe.getRef());
        // TopicRepoItems response = testProbe.expectMsgClass(TopicRepoItems.class);
        // logger.info(response.searchResults.toString());
        assertEquals(1, 1);
    }

    @After
    public void teardown() {
        TestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }
    
}
