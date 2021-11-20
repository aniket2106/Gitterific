package helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import models.repoDetails.RepoDetail;
import models.searchResult.SearchResults;
import play.libs.ws.WSClient;
import play.routing.RoutingDsl;
import play.server.Server;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static play.mvc.Results.ok;


public class GithubClientTest 
{
	private GithubClient githubClient;
    private WSClient wsTestClient;
    private Server server;

    @Before
    public void setup() {
        server = Server.forRouter(
                (components) -> RoutingDsl.fromComponents(components)
                        .GET("/search")
                        .routingTo(request -> {
                            if (request.queryString("q").isPresent() && !request.queryString("channelId").isPresent())
                            {
                                String searchKey = request.queryString("q").get().toLowerCase();
                                switch (searchKey) {
                                    case "java":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/testingdata/java.json"))));
                                    case "machine learning":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/testingdata/ml.json"))));
                                    case "php":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/testingdata/php.json"))));
                                    default:
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/empty.json"))));
                                }
                            } else {
                                String channelId = request.queryString("channelId").get();
                                switch (channelId) {
                                    case "UC0RhatS1pyxInC00YKjjBqQ":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelvideos/Java_UC0RhatS1pyxInC00YKjjBqQ.json"))));
                                    case "UCWr0mx597DnSGLFk1WfvSkQ":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelvideos/Python_UCWr0mx597DnSGLFk1WfvSkQ.json"))));
                                    case "UC-R1UuxHVDyNoJN0Tn4nkiQ":
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelvideos/Golang_UC-R1UuxHVDyNoJN0Tn4nkiQ.json"))));
                                    default:
                                        return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/empty.json"))));
                                }
                            }
                        })
                        .GET("/videos")
                        .routingTo(request -> {
                            String videoId = request.queryString("id").get();
                            switch (videoId) {
                                case "uhp3GbQiSRs":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/viewcount/Java_uhp3GbQiSRs.json"))));
                                case "OsKQw3qTMMk":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/viewcount/Python_OsKQw3qTMMk.json"))));
                                case "FxxkOfvY39c":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/viewcount/Golang_FxxkOfvY39c.json"))));
                                default:
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/empty.json"))));
                            }
                        })
                        .GET("/channels")
                        .routingTo(request -> {
                            String channelId = request.queryString("id").get();
                            switch (channelId) {
                                case "UC0RhatS1pyxInC00YKjjBqQ":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelinformation/Channel_Java_UC0RhatS1pyxInC00YKjjBqQ.json"))));
                                case "UCWr0mx597DnSGLFk1WfvSkQ":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelinformation/Channel_Python_UCWr0mx597DnSGLFk1WfvSkQ.json"))));
                                case "UC-R1UuxHVDyNoJN0Tn4nkiQ":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/channelinformation/Channel_Golang_UC-R1UuxHVDyNoJN0Tn4nkiQ.json"))));
                                default:
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/empty.json"))));
                            }
                        })
                        .GET("/commentThreads")
                        .routingTo(request -> {
                            String videoId = request.queryString("videoId").get();
                            switch (videoId) {
                                case "X2lIovmNsUY":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/comments/happy_video.json"))));
                                case "iupakooy3pU":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/comments/sad_video.json"))));
                                case "Bi7f1JSSlh8":
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/comments/neutral_video.json"))));
                                default:
                                    return ok(Objects.requireNonNull(DatasetHelper.jsonNodeFromJsonFile(new File("test/dataset/comments/zero_comments.json"))));
                            }
                        })
                        .build());
        wsTestClient = play.test.WSTestClient.newClient(server.httpPort());
        githubClient = new GithubClient(wsTestClient);
        githubClient.BASE_URL = "/";
    }
    
    @Test
    public void fetchRepos() throws Exception 
    {
        SearchResults actualJava = githubClient.fetchRepos("java").toCompletableFuture().get();
        SearchResults expectedJava = DatasetHelper.jsonFileToObject(new File("test/tesingdata/java.json"), SearchResults.class);
        assert expectedJava != null;
        Assert.assertEquals(expectedJava.toString(), actualJava.toString());

        SearchResults actualMl = githubClient.fetchRepos("machine learning").toCompletableFuture().get();
        SearchResults expectedMl = DatasetHelper.jsonFileToObject(new File("test/testingdata/ml.json"), SearchResults.class);
        assert expectedMl != null;
        Assert.assertEquals(expectedMl.toString(), actualMl.toString());

        SearchResults actualPhp = githubClient.fetchRepos("php").toCompletableFuture().get();
        SearchResults expectedPhp = DatasetHelper.jsonFileToObject(new File("test/testingdata/php.json"), SearchResults.class);
        assert expectedPhp != null;
        Assert.assertEquals(expectedPhp.toString(), actualPhp.toString());

        SearchResults actualNoResult = githubClient.fetchRepos("$jkl :<7tg 92+dj _3a) @(^! 9dv02*# 7&6 sn5^5").toCompletableFuture().get();
        SearchResults expectedEmptyJson = DatasetHelper.jsonFileToObject(new File("test/dataset/empty.json"), SearchResults.class);
        assert Objects.requireNonNull(expectedEmptyJson).getItems() == null;
        assert Objects.requireNonNull(actualNoResult).getItems() == null;
    }
    
    @Test
    public void fetchRepoDetail() throws Exception 
    {
        String actualJavaRepoDetail = githubClient.getViewCountByVideoId("uhp3GbQiSRs").toCompletableFuture().get();
        RepoDetail expectedJavaRepoDetail = DatasetHelper.jsonFileToObject(new File("test/dataset/viewcount/Java_uhp3GbQiSRs.json"), RepoDetail.class);
        assert expectedJavaRepoDetail != null;
        Assert.assertEquals(expectedJavaVideoItems.getItems().get(0).getStatistics().getViewCount(), actualJavaViewCountByVideoId);

        String actualPythonRepoDetail = youTubeApiClient.getViewCountByVideoId("OsKQw3qTMMk").toCompletableFuture().get();
        Videos expectedPythonRepoDetail = DatasetHelper.jsonFileToObject(new File("test/dataset/viewcount/Python_OsKQw3qTMMk.json"), Videos.class);
        assert expectedPythonVideoItems != null;
        Assert.assertEquals(expectedPythonVideoItems.getItems().get(0).getStatistics().getViewCount(), actualPythonViewCountByVideoId);

        String actualGolangViewCountByVideoId = youTubeApiClient.getViewCountByVideoId("FxxkOfvY39c").toCompletableFuture().get();
        Videos expectedGolangVideoItems = DatasetHelper.jsonFileToObject(new File("test/dataset/viewcount/Golang_FxxkOfvY39c.json"), Videos.class);
        assert expectedGolangVideoItems != null;
        Assert.assertEquals(expectedGolangVideoItems.getItems().get(0).getStatistics().getViewCount(), actualGolangViewCountByVideoId);

        String actualNoResult = youTubeApiClient.getViewCountByVideoId("!029 ( 02 _2 (@ 92020** 7&6 ^^5").toCompletableFuture().get();
        Videos expectedEmptyJson = DatasetHelper.jsonFileToObject(new File("test/dataset/empty.json"), Videos.class);
        assert Objects.requireNonNull(expectedEmptyJson).getItems() == null;
        String noData = "No Data";
        Assert.assertEquals(actualNoResult, noData);
    }
    
    @Test
    public void fetchIssues() throws Exception {
        SearchResults actualJava = youTubeApiClient.getVideosJsonByChannelId("UC0RhatS1pyxInC00YKjjBqQ", "java").toCompletableFuture().get();
        SearchResults expectedJava = DatasetHelper.jsonFileToObject(new File("test/dataset/channelvideos/Java_UC0RhatS1pyxInC00YKjjBqQ.json"), SearchResults.class);
        assert expectedJava != null;
        Assert.assertEquals(expectedJava.toString(), actualJava.toString());

        SearchResults actualPython = youTubeApiClient.getVideosJsonByChannelId("UCWr0mx597DnSGLFk1WfvSkQ", "python").toCompletableFuture().get();
        SearchResults expectedPython = DatasetHelper.jsonFileToObject(new File("test/dataset/channelvideos/Python_UCWr0mx597DnSGLFk1WfvSkQ.json"), SearchResults.class);
        assert expectedPython != null;
        Assert.assertEquals(expectedPython.toString(), actualPython.toString());

        SearchResults actualGolang = youTubeApiClient.getVideosJsonByChannelId("UC-R1UuxHVDyNoJN0Tn4nkiQ", "golang").toCompletableFuture().get();
        SearchResults expectedGolang = DatasetHelper.jsonFileToObject(new File("test/dataset/channelvideos/Golang_UC-R1UuxHVDyNoJN0Tn4nkiQ.json"), SearchResults.class);
        assert expectedGolang != null;
        Assert.assertEquals(expectedGolang.toString(), actualGolang.toString());

        SearchResults actualNoResult = youTubeApiClient.getVideosJsonByChannelId("!029 ( 02 _2 (@ 92020** 7&6 ^^5", "").toCompletableFuture().get();
        Videos expectedEmptyJson = DatasetHelper.jsonFileToObject(new File("test/dataset/empty.json"), Videos.class);
        assert Objects.requireNonNull(expectedEmptyJson).getItems() == null;
        assert Objects.requireNonNull(actualNoResult).getItems() == null;
    }
    
    @After
    public void destroy() throws IOException 
    {
        try 
        {
            wsTestClient.close();
        } 
        finally 
        {
            server.stop();
        }
    }
}
