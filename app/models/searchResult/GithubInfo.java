package models.searchResult;

public class GithubInfo {
	
	private String searchKeyword;
	private SearchResults searchResults;
	
	public GithubInfo() {
		
	}
	
	public GithubInfo(String searchKeyword, SearchResults searchResults) {
		this.searchKeyword = searchKeyword;
		this.searchResults = searchResults;
	}
	
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public String getSearchKeyword() {
		return this.searchKeyword;
	}
	
	public void setSearchResults(SearchResults searchResults) {
		this.searchResults = searchResults;
	}
	
	public SearchResults getSearchResults() {
		return this.searchResults;
	}
	
	@Override
	public String toString() {
		return "Search keyword = " + searchKeyword + ", data = " + searchResults + "\n\n";
	}

}
