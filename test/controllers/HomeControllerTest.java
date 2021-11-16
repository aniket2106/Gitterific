package controllers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import helper.GithubClient;
import helper.Session;
import models.Helper.SessionHelper;
import models.Helper.YoutubeAnalyzer;
import models.POJO.SearchResults.Id;
import models.POJO.SearchResults.SearchResultItem;
import models.POJO.SearchResults.Snippet;
import models.searchResult.SearchResults;

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
    @Test
    public void indexTestWithSession() {
        Http.RequestBuilder requestBuilder = Helpers.fakeRequest(routes.HomeController.index());
        requestBuilder.header("sessionId", "1");
        requestBuilder.session(Session.getSessionKey(), requestBuilder.getHeaders().get("sessionId").get());
        Result result = homeController.index(requestBuilder.build());
        Assert.assertEquals(OK, result.status());
    }
    
    
    /**
     * This method tests the <code>POST</code> request with a path of <code>/</code>, to Index page of the application
     * with valid Session and expects {@link Result} 200.
     *
     * @throws ExecutionException   Exception might occur on calling get() on {@link CompletableFuture}.
     * @throws InterruptedException Exception might occur on calling get() on {@link CompletableFuture}.
     * @author Rajan Shah
     */
    @Test
    public void fetchReposByKeywordsTest() throws ExecutionException, InterruptedException {
        SearchResults searchResults1 = new SearchResults();
        SearchResultItem searchResultItem = new SearchResultItem();
        searchResultItem.setId(new Id("abcXyz"));
        searchResultItem.setViewCount("123");
        searchResultItem.setSnippet(new Snippet("123", "channelTitle", "title", "description", "publishedAt", "publishTime"));
        searchResults1.setItems(new ArrayList<SearchResultItem>() {{
            add(searchResultItem);
        }});
        when(youtubeAnalyzerMock.fetchVideos("hello world")).thenReturn(CompletableFuture.supplyAsync(() -> searchResults1));
        Http.RequestBuilder requestBuilder = Helpers.fakeRequest(routes.YoutubeAnalyzerController.fetchVideosByKeywords());
        requestBuilder.header("User-Agent", "chrome");
        requestBuilder.session(SessionHelper.getSessionKey(), requestBuilder.getHeaders().get("User-Agent").get());
        Map<String, String[]> requestBody = new HashMap<>();
        String[] searchKeyWord = new String[]{"hello world"};
        requestBody.put("searchKeyword", searchKeyWord);
        requestBuilder.bodyFormArrayValues(requestBody);
        CompletionStage<Result> resultCompletionStage = youtubeAnalyzerController.fetchVideosByKeywords(requestBuilder.build());
        Assert.assertEquals(OK, resultCompletionStage.toCompletableFuture().get().status());
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
