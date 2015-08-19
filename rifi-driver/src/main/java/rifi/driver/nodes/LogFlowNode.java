package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

public class LogFlowNode extends AbstractFlowNode {

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        return sourceDefinition.log((String)options.get("message"));
    }
}
