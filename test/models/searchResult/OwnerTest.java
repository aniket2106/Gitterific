package models.searchResult;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OwnerTest {
    Owner owner = helper.getDummyOwner(1);
    Owner ownerSet = new Owner();

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

    @Test
    public void setIdTest() {
        ownerSet.setId(23);
        assertEquals(ownerSet.getId(), 23);
    }

    @Test
    public void setNodeIdTest() {
        ownerSet.setNodeId(helper.dummyUserNodeId);
        assertEquals(ownerSet.getNodeId(), helper.dummyUserNodeId);
    }

    @Test
    public void setLoginTest() {
        ownerSet.setLogin(helper.dummyUserName);
        assertEquals(ownerSet.getLogin(), helper.dummyUserName);
    }
}
