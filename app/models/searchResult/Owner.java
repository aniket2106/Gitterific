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

    public Owner() {
    }

    public Owner(int id, String nodeId) {
        this.id = id;
        this.nodeId = nodeId;
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

    @Override
    public String toString() {
        return "owner { id=" + Integer.toString(id) + ", node_id=" + nodeId + "}";
    }

}