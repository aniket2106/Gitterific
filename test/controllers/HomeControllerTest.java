package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import helper.GithubClient;
import models.Helper.YoutubeAnalyzer;
import models.searchResult.SearchResults;

import play.Application;
import play.api.i18n.MessagesApi;
import play.data.FormFactory;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    HomeController homeController;
    //FormFactory _mockFormFactory;
    //MessagesApi messagesApi;
    GithubClient githubClientMock;;
    SearchResults searchResults;
    
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        homeController = new HomeController();
        //_mockFormFactory = new GuiceApplicationBuilder().injector().instanceOf(FormFactory.class);
        //messagesApi = new GuiceApplicationBuilder().injector().instanceOf(MessagesApi.class);
        githubClientMock = mock(GithubClient.class);
        homeController.setHomeController(githubClientMock);
        //homeController.setFormFactory(_mockFormFactory);
        //homeController.setMessagesApi(messagesApi);
        searchResults = new SearchResults();
        
        
        when(githubClientMock.index(anyString())).thenReturn(CompletableFuture.supplyAsync(ArgumentMatchers::anyString));
        when(githubClientMock.issues(anyString())).thenReturn(CompletableFuture.supplyAsync(ArgumentMatchers::anyString));
        when(githubClientMock.repoByTopic(any(LinkedHashMap.class), anyString())).thenReturn(new HashMap<String, Long>());
        
        
    }
    
    
    @After
    public void destroy() {
    	homeController = null;
    	githubClientMock = null;
        //messagesApi = null;
        //mockFormFactory = null;
        searchResults = null;
    }
    
    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

}
