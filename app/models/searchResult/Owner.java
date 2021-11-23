package models.searchResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "node_id"
})

public class Owner {

    @JsonProperty("id")
    private int id;

    @JsonProperty("node_id")
    private String nodeId;
    
    @JsonProperty("login")
    private String login;

    public Owner() {
    }

    public Owner(int id, String nodeId, String login) {
        this.id = id;
        this.nodeId = nodeId;
        this.login = login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return this.nodeId;
    }
    
    public void setLogin(String login) {
    	this.login = login;
    }
    
    public String getLogin() {
    	return this.login;
    }

    @Override
    public String toString() {
        return "owner { id=" + Integer.toString(id) + ", node_id=" + nodeId + "}";
    }

}