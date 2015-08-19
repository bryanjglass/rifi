package rifi.driver.nodes;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
public interface FlowNode {
    ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception;

    Map<String, Object> getOptions();

    FlowNode getSourceNode();

    FlowNode getTargetNode();

    List<FlowNode> getChildNodes();

    void setOptions(Map<String, Object> options);

    void setSourceNode(FlowNode sourceNode);

    void setTargetNode(FlowNode targetNode);

    void setChildNodes(List<FlowNode> childNodes);
}
