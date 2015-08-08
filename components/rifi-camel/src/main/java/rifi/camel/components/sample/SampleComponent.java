package rifi.camel.components.sample;

import org.apache.camel.ComponentConfiguration;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class SampleComponent extends DefaultComponent {
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ComponentConfiguration createComponentConfiguration() {
        return super
                .createComponentConfiguration();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
