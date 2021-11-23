package models.repoDetails;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


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
