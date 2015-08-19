package rifi.driver.flows;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import rifi.driver.nodes.FlowNode;


public class FlowRouteBuilder extends RouteBuilder {
    private final FlowNode rootNode;

    public FlowRouteBuilder(rifi.driver.nodes.FlowNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public void configure() throws Exception {
        FlowNode current = rootNode;
        ProcessorDefinition previous = null;

        while(current != null) {
            previous = current.build(this, previous);
            current = current.getTargetNode();
        }
    }


}
