package models.searchResult;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SearchResultItemTest {

    private SearchResultItem test = helper.getDummySearchResultItem(1);
    private SearchResultItem testSet = new SearchResultItem();
    private Logger logger = LoggerFactory.getLogger("play");
    @Test
    public void getIdTest() {
        assertEquals(1, test.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals(test.getName(), helper.dummyItemName);
    }

    @Test
    public void getFullNameTest() {
        assertEquals(test.getFullName(), helper.dummyItemFullName);
    }

    @Test
    public void getNodeIdTest() {
        assertEquals(test.getNodeId(), helper.dummyNodeId);
    }

    // @Test
    // public void getOwnerTest() {
    //     assertEquals(test.getOwner(), helper.getDummyOwner(1));
    // }

    @Test
    public void getTopicsTest() {
        assertEquals(test.getTopics(), helper.getDummyTopics());
    }

    @Test
    public void setIdTest() {
        testSet.setId(1);
        assertEquals(1, testSet.getId());
    }

    @Test
    public void setNameTest() {
        testSet.setName(helper.dummyItemName);
        assertEquals(testSet.getName(), helper.dummyItemName);
    }

    @Test
    public void setFullNameTest() {
        testSet.setFullName(helper.dummyItemFullName);
        assertEquals(testSet.getFullName(), helper.dummyItemFullName);
    }

    @Test
    public void setNodeIdTest() {
        testSet.setNodeId(helper.dummyNodeId);
        assertEquals(testSet.getNodeId(), helper.dummyNodeId);
    }

    @Test
    public void getOwnerTest() {
        testSet.setOwner(helper.getDummyOwner(1));
        assertEquals(testSet.getOwner().getId(), 51);
    }

    @Test
    public void setTopicsTest() {
        testSet.setTopics(helper.getDummyTopics());
        assertEquals(testSet.getTopics(), helper.getDummyTopics());
    }

    @Test
    public void toStringTest() {
        logger.info(test.toString());
        assertEquals(test.toString(), "SearchResultItem{id=1,Snippet=null, full_name=dummyFullName, node_id=dummynodeid}");
    }
}
