package rifi.core;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ProcessorDefinition;

import java.util.ArrayList;
import java.util.List;

public class FlowBuilder {
    Graph<Component, String> graph;

    public FlowBuilder(Graph<Component, String> graph) {
        this.graph = graph;
    }

    public void build() {
        List<Node<Component, String>> rootNodes = new ArrayList<>();
        CamelContext camelContext = new DefaultCamelContext();
        Context context = new Context();
        context.routeBuilder = new RouteBuilder(camelContext) {
            @Override
            public void configure() throws Exception {
                // Do nothing
            }
        };

        for (Node<Component, String> componentNode : this.graph.getNodes()) {
            if(componentNode.getIncoming() == null) {
                rootNodes.add(componentNode);
            }
        }

        for (Node<Component, String> rootNode : rootNodes) {
            ProcessorDefinition previous = null;
            Node<Component, String> node = rootNode;

            while(node != null) {
                previous = node.getData().build(context, previous);
                if(node.getOutgoing() != null) {
                    node = node.getOutgoing().getTarget();
                } else {
                    node = null;
                }
            }
        }

    }
}
