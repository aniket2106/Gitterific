
package helper;

import models.searchResult.SearchResults;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session {
    private static final HashMap<String, LinkedHashMap<String, SearchResults>> sessionSearchResultsBySearchKeywordHashMap = new HashMap<>();
    private static final String SESSION_KEY = "sessionId";
    private static Logger logger = LoggerFactory.getLogger("play");

    public static String getSessionKey() {
        return SESSION_KEY;
    }

    public static LinkedHashMap<String, SearchResults> getSearchResultsHashMapFromSession(Http.Request request) {
        String key = getSessionValue(request);
        logger.info(key);
        return sessionSearchResultsBySearchKeywordHashMap.get(key);
    }

 
    public static void setSessionSearchResultsHashMap(Http.Request request, String searchKeyword, SearchResults searchResults) {
        String key = getSessionValue(request);
        LinkedHashMap<String, SearchResults> searchResultsLinkedHashMap = getSearchResultsHashMapFromSession(request);
        if (searchResultsLinkedHashMap == null) {
            searchResultsLinkedHashMap = new LinkedHashMap<>();
        }
        searchResultsLinkedHashMap.put(searchKeyword, searchResults);
        reverseHash(request);
        sessionSearchResultsBySearchKeywordHashMap.put(key, searchResultsLinkedHashMap);
        reverseHash(request);
    }

    public static boolean isSessionExist(Http.Request request) {
        return request.session().get(SESSION_KEY).orElse(null) != null;
    }

    public static String getSessionValue(Http.Request request) {
        return request.session().get(SESSION_KEY).orElse(null);
    }

    public static void reverseHash(Http.Request request) {
        String sessionKey = request.session().get(SESSION_KEY).orElse(null);
        LinkedHashMap<String, SearchResults> searchResultsLinkedHashMap = sessionSearchResultsBySearchKeywordHashMap.get(sessionKey);
        if (searchResultsLinkedHashMap != null) {
            logger.info(Arrays.toString(searchResultsLinkedHashMap.keySet().toArray()));
            List<String> hashKeys = new ArrayList<>(searchResultsLinkedHashMap.keySet());
            LinkedHashMap<String, SearchResults> temp = new LinkedHashMap<>();
            Collections.reverse(hashKeys);
            for (String strKey : hashKeys) {
                temp.put(strKey, searchResultsLinkedHashMap.get(strKey));
            }
            sessionSearchResultsBySearchKeywordHashMap.put(sessionKey, temp);
        }
    }
}