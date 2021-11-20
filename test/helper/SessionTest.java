package helper;

import helper.Session;
import models.searchResult.SearchResults;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;


public class SessionTest WithApplication
{
	@Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    Http.RequestBuilder request;
    
    @Before
    public void init() 
    {
        request = fakeRequest(GET, "/");
        request.header("sessionId", "1");
        request.session(Session.getSessionKey(), request.getHeaders().get("sessionId").get());
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
    @Test
    public void testIsSessionExist() {
        assertTrue(Session.isSessionExist(request.build()));
    }
    @Test
    public void testSearchResultsSessionData() {
        SearchResults searchResults = new SearchResults();
        LinkedHashMap<String, SearchResults> searchResultsLinkedHashMap = new LinkedHashMap<String, SearchResults>() {{
            put("searchKeyword", searchResults);
        }};
        Session.setSessionSearchResultsHashMap(request.build(), "searchKeyword", searchResults);
        assertEquals(searchResultsLinkedHashMap.get("searchKeyword"), Session.getSearchResultsHashMapFromSession(request.build()).get("searchKeyword"));
    }
    @Test
    public void getSessionValueTest() {
        assertEquals(request.getHeaders().get("sessionId").get(), Session.getSessionValue(request.build()));
    }
   
}
