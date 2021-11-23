package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import models.searchResult.Owner;

public class OwnerTest {
    Owner owner = helper.getDummyOwner(1);

    @Test
    public void getIdTest() {
        assertEquals(owner.getId(), 51);
    }

    @Test
    public void getNodeIdTest() {
        assertEquals(owner.getNodeId(), helper.dummyUserNodeId);
    }

    @Test
    public void getLoginTest() {
        assertEquals(owner.getLogin(), helper.dummyUserName);
    }
}
