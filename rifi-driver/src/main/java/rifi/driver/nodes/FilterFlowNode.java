package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.SimpleBuilder;
import org.apache.camel.model.ProcessorDefinition;

public class FilterFlowNode extends AbstractFlowNode {

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        return sourceDefinition.filter(SimpleBuilder.simple("${file:size} > 100"));
    }
}
