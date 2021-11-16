package helper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import models.searchResult.GithubInfo;
import models.searchResult.SearchResults;
import play.mvc.Http;
import play.mvc.Http.Request;

public class SessionHelper {
	
/*
	private static final HashMap<List<GithubInfo> > sessionSearchResultsBySearchKeywordHashMap = new HashMap<>();
	private static final String SESSION_KEY = "sessionId";
	
	public static  String getSesssionKey() {
		return SESSION_KEY;
	}
	
	public static LinkedHashMap<List<GithubInfo> githubInfos> getSearchResultsHashMapFromSession(Http.Request request){
		 String key = getSessionValue(request);
	        return sessionSearchResultsBySearchKeywordHashMap.get(key);
	}
	
	public static void setSessionSearchResultsHashMap(Http.Request request, List<GithubInfo> githubInfos) {
        String key = getSessionValue(request);
        LinkedHashMap<List<GithubInfo> githubInfos> searchResultsLinkedHashMap = getSearchResultsHashMapFromSession(request);
        if (searchResultsLinkedHashMap == null) {
            searchResultsLinkedHashMap = new LinkedHashMap<>();
        }
        searchResultsLinkedHashMap.put(searchKeyword, searchResults);
        sessionSearchResultsBySearchKeywordHashMap.put(key, searchResultsLinkedHashMap);
   
		
	}
	
	public static boolean isSessionExist(Http.Request request) {
		return request.session().get(SESSION_KEY).orElse(null) != null;
	}
	private static String getSessionValue(Request request) {
	
		return request.session().get(SESSION_KEY).orElse(null);
	}
	
	*/
}
