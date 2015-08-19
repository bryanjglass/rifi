package rifi.driver.graph;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Node {
    String value;
    @JsonIgnore
    Edge incoming;
    @JsonIgnore
    Edge outgoing;


    public Node(String value) {
        this.value = value;
    }

    public Edge getIncoming() {
        return incoming;
    }

    public void setIncoming(Edge incoming) {
        this.incoming = incoming;
    }

    public Edge getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Edge outgoing) {
        this.outgoing = outgoing;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
