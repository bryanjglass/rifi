package rifi.engine;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;


public class Application {
    @Autowired
    CamelContext camelContext;

    @PostConstruct
    public void init() {
        System.out.println("CONTEXT NAME: " + camelContext.getName());
    }
}
