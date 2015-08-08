package rifi.camel.components.sample;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

public class SampleProducer extends DefaultProducer {

    public SampleProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
