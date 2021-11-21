package models;

import org.junit.Test;

import models.repoDetails.IssueItem;
import play.mvc.Http;
import play.mvc.Result;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;


public class IssueItemTest 
{
	String url = "https://github.com/ferdouszislam/Weather-WaterLevel-Prediction-ML/issues/9";
    int number = 9;
    String title = "Create Notebook with Generalized Functions for Feature Selection";
    
    private IssueItem test = new IssueItem(url, number, title);

    @Test
    public void getUrlTest(){
        assertEquals(url, test.getUrl());
    }

    @Test
    public void getNumberTest(){
        assertEquals(number, test.getNumber());
    }

    @Test
    public void getTitleTest(){
        assertEquals(title, test.getTitle());
    }

    
}
