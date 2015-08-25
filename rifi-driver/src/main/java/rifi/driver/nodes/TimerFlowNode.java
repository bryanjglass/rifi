package rifi.driver.nodes;

import org.apache.camel.ComponentConfiguration;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.timer.TimerComponent;
import org.apache.camel.language.constant.ConstantLanguage;
import org.apache.camel.model.ProcessorDefinition;

public class TimerFlowNode extends AbstractFlowNode {
    @Override
    public String getName() {
        return "timer";
    }

    @Override
    public ProcessorDefinition build(RouteBuilder route, ProcessorDefinition sourceDefinition) throws Exception {
        TimerComponent component = route.getContext().getComponent("timer", TimerComponent.class);
        ComponentConfiguration componentConfiguration = component.createComponentConfiguration();

        String name = component.getAndRemoveParameter(options, "name", String.class);
        componentConfiguration.setBaseUri(String.format("timer:%s", name));
        Endpoint endpoint = componentConfiguration.createEndpoint();

        ProcessorDefinition definition;

        if(sourceDefinition == null) {
            definition = route.from(endpoint).setBody(ConstantLanguage.constant("hello"));
        } else {
            definition = sourceDefinition.to(endpoint);
        }

        return definition;
    }
}
