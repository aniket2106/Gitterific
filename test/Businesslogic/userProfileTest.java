import Businesslogic;
import models.publicUserProfile;
import java.io.IOException;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import java.util.*;
import org.junit.Before;
import org.junit.After;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor;
import java.util.stream.*;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import org.junit.ClassRule;
import akka.actor.typed.javadsl.Behaviors;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import java.time.Duration;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class userProfileTest extends Mockito{

    static ActorSystem actorSystem;
    static List<publicUserProfile> l;

    public static class userProfileTestMock extends AbstractActor {
        public String API = "http://localhost:9000";
        HttpResponse response = null;

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, a -> {
                        List<publicUserProfile> temp = l;
                        System.out.println(temp);
                        sender().tell(temp, self());
                    })
                    .build();
        }

        public JSONObject getUserData(String username){
            JSONObject temp = new JSONObject();
            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + URLEncoder.encode(username,"UTF-8"))).build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Object obj = new JSONParser().parse(String.valueOf(response.body()));
                temp = (JSONObject) obj;
            }catch (Exception e)
            {

            }
            return temp;
        }

        public List<publicUserProfile> getData(String username) {
            List<publicUserProfile> array = new ArrayList<>();
            JSONObject array1 = this.getUserData(username);
            var temp = array1;
            try{
                array.add(new publicUserProfile((String) temp.get("login"),(String) temp.get("name"),(String) temp.get("company"),(String) temp.get("location"),(String) temp.get("email"),(String) temp.get("hire"),(String) temp.get("bio"),(String) temp.get("twitter_username"),(Long) temp.get("public_repos"),(Long) temp.get("followers"),(Long) temp.get("following")));
            }catch(Exception e){}
            return array;
        }
    }

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Mock
    private userProfile up;
    private ActorRef mainActor;

    @Before
    public void setup() {
        actorSystem = ActorSystem.create();
        up = mock(userProfile.class);
        l = new ArrayList<publicUserProfile>();
        l.add(new publicUserProfile("aniket2106","Aniket","Google","Canada","abc@gmail.com","hireable","bio","aniket2106",5L,10L,11L));
        when(up.getData("call")).thenReturn(l);
    }

    @Test
    public void testGetData(){
        final Props props = Props.create(userProfileTestMock.class);
        mainActor = actorSystem.actorOf(props);

        final TestKit testProbe = new TestKit(actorSystem);
        mainActor.tell("call",testProbe.getRef());
        List a = testProbe.expectMsgClass(List.class);
        System.out.println("abc" + a);
        assertEquals(l,a);
    }

    @After
    public void teardown() {
        TestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }
}