package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

public class LogNode extends Node {

    @Override
    public String getType() {
        return "log";
    }

    @Override
    public ProcessorDefinition build(RouteBuilder routeBuilder, ProcessorDefinition parent) {
        ProcessorDefinition definition = parent.log((String)options.get("message"));

        if(target != null) {
            return target.build(routeBuilder, definition);
        } else {
            return definition;
        }
    }
}
