package repoByTopicActorsTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.searchResult.Owner;
import models.searchResult.SearchResultItem;

public class helper {
    
    static private int counter = 1;

    static public String dummyItemName = "dummyName";
    static public String dummyItemFullName = "dummyFullName";
    static public String dummyNodeId = "dummynodeid";
    static public String dummyUserNodeId = "dummyUserNodeId";
    static public String dummyUserName = "dummyUserName";


    static public Owner getDummyOwner(int flag) {
        return new Owner(flag + 50, dummyUserNodeId, dummyUserName);
    }

    static public List<String> getDummyTopics() {
        return Arrays.asList("topic A", "topic B");
    }

    static public SearchResultItem getDummySearchResultItem(int flag) {
        return new SearchResultItem(flag, dummyItemName, dummyItemFullName, dummyNodeId, getDummyOwner(flag), getDummyTopics());
    }

    static public List<SearchResultItem> getDummySearchResults() {
        List<SearchResultItem> dummyItems = new ArrayList<>();
        for(int i=0; i<10; i++) {
            dummyItems.add(getDummySearchResultItem(counter));
            counter += 1;
        }

        return dummyItems;
    }

}
