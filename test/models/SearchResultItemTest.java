package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import models.searchResult.SearchResultItem;

public class SearchResultItemTest {

    SearchResultItem searchResultItem = helper.getDummySearchResultItem(1);

    @Test
    public void getIdTest() {
        assertEquals(searchResultItem.getId(), 1);
    }

    @Test
    public void getNameTest() {
        assertEquals(searchResultItem.getName(), helper.dummyItemName);
    }

    @Test
    public void getFullNameTest() {
        assertEquals(searchResultItem.getFullName(), helper.dummyItemFullName);
    }

    @Test
    public void getNodeIdTest() {
        assertEquals(searchResultItem.getNodeId(), helper.dummyNodeId);
    }

    @Test
    public void getOwnerTest() {
        assertEquals(searchResultItem.getOwner(), helper.getDummyOwner(1));
    }

    @Test
    public void getTopicsTest() {
        assertEquals(searchResultItem.getTopics(), helper.getDummyTopics());
    }
}
