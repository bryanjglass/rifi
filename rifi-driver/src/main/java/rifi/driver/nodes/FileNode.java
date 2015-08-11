package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;


public class FileNode extends Node {
    @Override
    public ProcessorDefinition build(RouteBuilder routeBuilder, ProcessorDefinition parent) {
        ProcessorDefinition definition;

        if(parent == null) {
            definition = routeBuilder.from("file://fromdirectory");
        } else {
            definition = parent.to("file://todirectory");
        }

        if(target != null) {
            return target.build(routeBuilder, definition);
        } else {
            return definition;
        }
    }
}
