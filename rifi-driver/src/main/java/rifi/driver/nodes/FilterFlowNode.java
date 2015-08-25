package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.SimpleBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Component;

@Component
public class FilterFlowNode extends AbstractFlowNode {

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        return sourceDefinition.filter(SimpleBuilder.simple("${file:size} > 100"));
    }
}
