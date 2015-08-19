package rifi.driver.nodes;

import org.apache.camel.CamelContext;
import org.apache.camel.ComponentConfiguration;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spring.SpringCamelContext;
import rifi.driver.flows.*;

import java.util.List;

@rifi.driver.flows.FlowNode(name = "file")
public class FileFlowNode extends AbstractFlowNode implements TestableFlowNode {

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        ProcessorDefinition definition;

        FileComponent component = route.getContext().getComponent("file", FileComponent.class);
        String path = component.getAndRemoveParameter(options, "directoryName", String.class);

        ComponentConfiguration configuration = component.createComponentConfiguration();
        configuration.setParameters(options);
        configuration.setBaseUri("file://" + path);

        Endpoint endpoint = configuration.createEndpoint();

        if(sourceDefinition == null) {
            definition = route.from(endpoint);
        } else {
            definition = sourceDefinition.to(endpoint);
        }

        return definition;
    }

    @Override
    public Object test(Object input) throws Exception {
        SpringCamelContext context = new SpringCamelContext();
        FlowRouteBuilder builder = new FlowRouteBuilder(this);
        builder.onCompletion().to("mock:result");
        context.addRoutes(builder);
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // mock all endpoints
                mockEndpoints();
            }
        });
        context.start();

        MockEndpoint resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
        List<Exchange> exchanges = resultEndpoint.getExchanges();
        resultEndpoint.reset();
        context.stop();
        context.destroy();
        return exchanges;
    }

    private Endpoint createEndpoint(CamelContext context) throws Exception {
        FileComponent component = context.getComponent("file", FileComponent.class);
        String path = component.getAndRemoveParameter(options, "directoryName", String.class);

        ComponentConfiguration configuration = component.createComponentConfiguration();
        configuration.setParameters(options);
        configuration.setBaseUri("file://" + path);

        return configuration.createEndpoint();
    }
}
