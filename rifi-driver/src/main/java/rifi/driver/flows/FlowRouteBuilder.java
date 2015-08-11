package rifi.driver.flows;

import org.apache.camel.builder.RouteBuilder;
import rifi.driver.nodes.Node;


public class FlowRouteBuilder extends RouteBuilder {
    private final Node rootNode;

    public FlowRouteBuilder(Node rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public void configure() throws Exception {
        rootNode.build(this, null);
    }
}
