package controllers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import org.mockito.thenReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import helper.GithubClient;
import helper.Session;

import models.searchResult.*;

import play.Application;
import play.api.i18n.MessagesApi;
import play.api.test.Helpers;
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
    GithubClient githubClientMock;;
    SearchResults searchResults;
    
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        homeController = new HomeController();
        githubClientMock = mock(GithubClient.class);
        homeController.setHomeController(githubClientMock);
        searchResults = new SearchResults();
        
        
        when(githubClientMock.fetchRepos(anyString())).thenReturn(CompletableFuture.supplyAsync(ArgumentMatchers::anyString));
        //when(githubClientMock.fetchRepoDetail(anyString())).thenReturn(CompletableFuture.supplyAsync(ArgumentMatchers::anyString));

        //        when(githubClientMock.fetchIssues(anyString())).thenReturn(CompletableFuture.supplyAsync(ArgumentMatchers::anyString));
//        when(githubClientMock.fetchReposByTopic(any(LinkedHashMap.class), anyString())).thenReturn(new HashMap<String, Long>());
        
        
    }
    
    @Test
    public void indexTestWithSession() {
        Http.RequestBuilder requestBuilder = Helpers.fakeRequest(routes.HomeController.index());
        requestBuilder.header("sessionId", "1");
        requestBuilder.session(Session.getSessionKey(), requestBuilder.getHeaders().get("sessionId").get());
        Result result = homeController.index(requestBuilder.build());
        Assert.assertEquals(OK, result.status());
    }
    
    @Test
    public void fetchReposByKeywordsTest() throws ExecutionException, InterruptedException {
    	
        SearchResults searchResults1 = new SearchResults();
        SearchResultItem searchResultItem = new SearchResultItem();        
        searchResultItem.setId(new id("123"));
        searchResultItem.setName("zara");
        searchResultItem.setFullName("hsj");
        searchResultItem.setNodeId("44");
        searchResultItem.setOwner("44");

        searchResultItem.setTopics(Arrays.asList("ai", "python", "machine-learning"));
   
        searchResults1.setItems(new ArrayList<SearchResultItem>() {{
            add(searchResultItem);
        }});
        
        when(githubClientMock.fetchRepos("machine learning")).thenReturn(CompletableFuture.supplyAsync(() -> searchResults1));
        Http.RequestBuilder requestBuilder = Helpers.fakeRequest(routes.HomeController.index());
        requestBuilder.header("sessionId", "1");
        requestBuilder.session(Session.getSessionKey(), requestBuilder.getHeaders().get("sessionId").get());
        Map<String, String[]> requestBody = new HashMap<>();
        String[] searchKeyWord = new String[]{"machine learning"};
        requestBody.put("searchKeyword", searchKeyWord);
        requestBuilder.bodyFormArrayValues(requestBody);
        CompletionStage<Result> resultCompletionStage = homeController.index(requestBuilder.build());
        Assert.assertEquals(OK, resultCompletionStage.toCompletableFuture().get().status());
    }
   
    
	@After
    public void destroy() {
    	homeController = null;
    	githubClientMock = null;
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
