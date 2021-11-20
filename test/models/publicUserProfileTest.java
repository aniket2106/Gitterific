package models;

import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import java.util.Date;

public class publicUserProfileTest {
    String userid = "aniket2106";
    String name = "aniket";
    String company = "Spark";
    String location = "india";
    String email = "aniket.tailor@gmail.com";
    String hire = "no";
    String bio = "empty";
    String twitter_username = "aniket2106";
    long public_repos = 10;
    long followers = 5;
    long following = 2;

    private publicUserProfile test = new publicUserProfile(userid,name,company,location,email,hire,bio,twitter_username,public_repos,followers,following);

    @Test
    public void getUseridTest(){
        assertEquals("aniket2106", test.getUserid());
    }

    @Test
    public void getNameTest(){
        assertEquals("aniket", test.getName());
    }

    @Test
    public void getCompanyTest(){
        assertEquals("Spark", test.getCompany());
    }

    @Test
    public void getLocationTest(){
        assertEquals("india", test.getLocation());
    }

    @Test
    public void getEmailTest(){
        assertEquals("aniket.tailor@gmail.com", test.getEmail());
    }

    @Test
    public void getHireTest(){
        assertEquals("no", test.getHire());
    }

    @Test
    public void getBioTest(){
        assertEquals("empty", test.getBio());
    }

    @Test
    public void getTwitterUsernameTest(){
        assertEquals("aniket2106", test.getTwitterUsername());
    }

    @Test
    public void getPublicReposTest(){
        assertEquals(public_repos, test.getPublic_repos());
    }

    @Test
    public void getFollowersTest(){
        assertEquals(followers, test.getFollowers());
    }

    @Test
    public void getFollowingTest(){
        assertEquals(following, test.getFollowing());
    }
}