package models.repoDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "html_url",
        "number",
        "title"
})


public class IssueItem {
    
    @JsonProperty("html_url")
    private String url;

    @JsonProperty("number")
    private int number;

    @JsonProperty("title")
    private String title;

    public IssueItem() {

    }

    public IssueItem(String url, int number, String title) {
        this.url = url;
        this.number = number;
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "url = " + url + ", title = " + title;
    }

}
