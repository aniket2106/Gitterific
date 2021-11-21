package models;

import org.junit.Test;

import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import models.searchResult.Owner;
import play.mvc.Http;
import play.mvc.Result;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RepoDetailTest 
{
	int id = 418435938;
    String nodeId = "R_kgDOGPDTYg";
    String fullName = "ferdouszislam/Weather-WaterLevel-Prediction-ML";
    boolean isPrivate = false;
    Owner owner = null;
    String htmlUrl = "ferdouszislam/Weather-WaterLevel-Prediction-ML";
    String description = "Predict rainfall and waterlevel from other relevant weather data using data-mining techniques and machine learning. Weather dataset source: http://www.brri.gov.bd/site/page/2ded3c9f-593b-4434-9dda-20e6f77d47ab/-, Water-level dataset source: http://www.hydrology.bwdb.gov.bd/index.php?pagetitle=water_level&sub3=185&_subid=131&id=125&id2=126.";
    String createdAt = "2021-10-18T09:42:35Z";
    int watchersCount = 0;
    int forksCount = 0;
    List<String> topics = Arrays.asList(new String[]{"data-mining", "machine-learning", "python"});
    String visibility = "public";
    
    private RepoDetail test = new RepoDetail(id, nodeId, fullName, isPrivate, owner, htmlUrl, description, createdAt, watchersCount, forksCount, topics, visibility);

    @Test
    public void getIdTest(){
        assertEquals(id, test.getId());
    }

    @Test
    public void getNodeIdTest(){
        assertEquals(nodeId, test.getNodeId());
    }

    @Test
    public void getfullNameTest(){
        assertEquals(fullName, test.getFullName());
    }
    
    @Test
    public void getisPrivateTest(){
        assertEquals(isPrivate, test.getIsPrivate());
    }

    @Test
    public void getownerTest(){
        assertEquals(owner, test.getOwner());
    }
    
    @Test
    public void gethtmlUrlTest(){
        assertEquals(htmlUrl, test.getHtmlUrl());
    }

    @Test
    public void getDescriptionTest(){
        assertEquals(description, test.getDescription());
    }

    @Test
    public void getCreatedAtTest(){
        assertEquals(createdAt, test.getCreatedAt());
    }
    
    @Test
    public void getWatchersCountTest(){
        assertEquals(watchersCount, test.getWatchersCount());
    }

    @Test
    public void getforksCountTest(){
        assertEquals(forksCount, test.getForksCount());
    }

    @Test
    public void getTopicsTest(){
        assertEquals(topics, test.getTopics());
    }
    
    @Test
    public void getVisibilityTest(){
        assertEquals(visibility, test.getVisibility());
    }
}
