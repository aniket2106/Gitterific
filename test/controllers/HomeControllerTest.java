package controllers;

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

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testSearchResult() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/?searchKeyword=dummySearch");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
    /*
    @Test
    public void testUserProfile() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/aniket2106");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
    */
    
    @Test
    public void testRepoByTopic() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/search/topic/accounting");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
   
    @Test
    public void testIssues() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/repo/kiyokosaito/MLS-project");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
}