package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.SimpleBuilder;
import org.apache.camel.model.ProcessorDefinition;

public class FilterNode extends Node {

    @Override
    public ProcessorDefinition build(RouteBuilder routeBuilder, ProcessorDefinition parent) {
        ProcessorDefinition definition = parent.filter(SimpleBuilder.simple("${file:size} > 100"));
        return target.build(routeBuilder, definition);
    }
}
