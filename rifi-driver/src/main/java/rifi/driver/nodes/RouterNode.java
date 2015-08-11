package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;

import static org.apache.camel.builder.SimpleBuilder.simple;

public class RouterNode extends Node {

    @Override
    public ProcessorDefinition build(RouteBuilder routeBuilder, ProcessorDefinition parent) {
        ChoiceDefinition definition = parent.choice();

        for (int i = 0; i < children.size(); i++) {
            children.get(i).build(routeBuilder, definition.when(simple("${in.header.ROUTE_INDEX} == " + i))).endChoice();
        }

        definition.otherwise().log("dropped");

        return definition.end();
    }
}
