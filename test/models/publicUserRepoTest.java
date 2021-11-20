package models;

import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import java.util.Date;

public class publicUserRepoTest {
    String repoNames = "APP assignment";

    private publicUserRepo repo = new publicUserRepo(repoNames);

    @Test
    public void getRepoNamesTest(){
        assertEquals("APP assignment", repo.getRepoNames());
    }
}