package rifi.core.components;

import org.apache.camel.ComponentConfiguration;
import org.apache.camel.Endpoint;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.ProcessorDefinition;
import rifi.core.Context;
import rifi.core.DefaultComponent;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bjglass
 * Date: 8/25/15
 * Time: 12:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class FileFlowComponent extends DefaultComponent {
    @Override
    public ProcessorDefinition build(Context context, ProcessorDefinition source) throws Exception {
        FileComponent component = context.getRouteBuilder().getContext().getComponent("file", FileComponent.class);
        ComponentConfiguration componentConfiguration = component.createComponentConfiguration();

        componentConfiguration.setBaseUri("file:///tmp/foo");
        componentConfiguration.setParameters(getOptions());

        Endpoint endpoint = componentConfiguration.createEndpoint();
        if(source == null) {
            return new FromDefinition(endpoint);
        } else {
            return
        }


        return null;
    }

    @Override
    public String getName() {
        return "file";
    }

}
