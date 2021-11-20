package models.searchResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"items"})

public class SearchResults {

    @JsonProperty("items")
    private List<SearchResultItem> items = null;

    public SearchResults() {
    }

    public List<SearchResultItem> getItems() {
        return this.items;
    }

    public void setItems(List<SearchResultItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "items=" + items +
                "}";
    }

}