package models.repoDetails;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import models.searchResult.Owner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "node_id",
        "full_name",
        "private",
        "owner",
        "html_url",
        "description",
        "created_at",
        "watchers_count",
        "forks_count",
        "topics",
        "visibility",

})

public class RepoDetail {
    
    @JsonProperty("id")
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private Boolean isPrivate;

    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("watchers_count")
    private int watchersCount;

    @JsonProperty("forks_count")
    private int forksCount;

    @JsonProperty("topics")
    private List<String> topics = null;

    @JsonProperty("visibility")
    private String visibility;

    private List<IssueItem> issueItems = null;

    public RepoDetail() {

    }
    
    public RepoDetail(int id, String nodeId, String fullName, Boolean isPrivate, Owner owner,  String htmlUrl, String description, String createdAt, int watchersCount, int forksCount, List<String> topics, String visibility)
    {
    	this.id = id;
    	this.nodeId = nodeId;
    	this.fullName = fullName;
    	this.isPrivate = isPrivate;
    	this.owner = owner;
    	this.htmlUrl = htmlUrl;
    	this.description = description;
    	this.createdAt = createdAt;
    	this.watchersCount = watchersCount;
    	this.forksCount = forksCount;
    	this.topics = topics;
    	this.visibility = visibility;
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<IssueItem> getIssueItems() {
        return issueItems;
    }

    public void setIssueItems(List<IssueItem> issueItems) {
        this.issueItems = issueItems;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    @Override
    public String toString() {
        return nodeId;
    }
}
