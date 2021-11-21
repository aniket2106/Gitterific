package models.searchResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "publishedAt",
        "channelId",
        "title",
        "description",
        "channelTitle",
        "publishTime"
})

public class Snippet {
    @JsonProperty("publishedAt")
    private String publishedAt;
    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("channelTitle")
    private String channelTitle;
    @JsonProperty("publishTime")
    private String publishTime;

    public Snippet() {
    }

    public Snippet(String channelId, String channelTitle, String title, String description, String publishedAt, String publishTime) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.description = description;
        this.title = title;
        this.publishedAt = publishedAt;
        this.publishTime = publishTime;
    }

 
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "publishedAt='" + publishedAt + '\'' +
                ", channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
