package rifi.driver.nodes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
public abstract class AbstractFlowNode implements FlowNode {

    protected Map<String, Object> options = new HashMap<>();

    @JsonBackReference
    protected FlowNode sourceNode;

    @JsonManagedReference
    protected FlowNode targetNode;

    @JsonManagedReference
    protected List<FlowNode> childNodes = new ArrayList<>();

    @Override
    public Map<String, Object> getOptions() {
        return options;
    }

    @Override
    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    @Override
    public FlowNode getSourceNode() {
        return sourceNode;
    }

    @Override
    public void setSourceNode(FlowNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    @Override
    public FlowNode getTargetNode() {
        return targetNode;
    }

    @Override
    public void setTargetNode(FlowNode targetNode) {
        this.targetNode = targetNode;
    }

    @Override
    public List<FlowNode> getChildNodes() {
        return childNodes;
    }

    @Override
    public void setChildNodes(List<FlowNode> childNodes) {
        this.childNodes = childNodes;
    }
}
