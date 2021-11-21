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

/**
 * HomeControllerTest Test Class
 * This class tests only the Result Status and not the response body.
 */
public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    
    /**
     * This method checks the <code>GET</code> request with a path of <code>/</code>, to Index page of the application
     */
    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    /**
     * This method tests the <code>GET</code> request with a path of <code>/searchKeyword</code>, to Index page of the application
     * with dummySearch keyword.
     */
    @Test
    public void testSearchResult() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/?searchKeyword=dummySearch");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    /**
     * This method tests the <code>GET</code> request with a path of <code>/key</code>.
     */
    @Test
    public void testUserProfile() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/aniket2106");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    
    /**
     * This method tests the <code>GET</code> request with a path of <code>/search/topic</code>, to topic page of the application
     * and expects {@link Result} 10.
     */
    @Test
    public void testRepoByTopic() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/search/topic/accounting");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
    
    /**
     This method tests the <code>GET</code> request with a path of <code>/repo/issues</code>, to index page of the application
     * and expects {@link Result} 20.
     */
   
    @Test
    public void testIssues() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/repo/kiyokosaito/MLS-project");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
}