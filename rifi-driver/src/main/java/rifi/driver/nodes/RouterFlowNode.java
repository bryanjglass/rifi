package rifi.driver.nodes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;

import static org.apache.camel.builder.SimpleBuilder.simple;

public class RouterFlowNode extends AbstractFlowNode {

    @Override
    public String getName() {
        return "router";
    }

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        ChoiceDefinition definition = sourceDefinition.choice();

        for (FlowNode childNode : childNodes) {
            FlowNode current = childNode;
            ProcessorDefinition previous = definition.when().simple("${in.header.ROUTE_INDEX} == 1");

            while(current != null) {
                previous = current.build(route, previous);
                current = current.getTargetNode();
            }

            previous.endChoice();
        }

        definition.otherwise().log("dropped");

        return definition.end();
    }
}
