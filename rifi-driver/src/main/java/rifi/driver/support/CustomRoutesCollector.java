package rifi.driver.support;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.spring.boot.CamelSpringBootInitializationException;
import org.apache.camel.spring.boot.RoutesCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

public class CustomRoutesCollector extends RoutesCollector {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRoutesCollector.class);
    private final ApplicationContext applicationContext;
    private final List<CamelContextConfiguration> camelContextConfigurations;

    public CustomRoutesCollector(ApplicationContext applicationContext, List<CamelContextConfiguration> camelContextConfigurations) {
        super(applicationContext, camelContextConfigurations);

        this.applicationContext = applicationContext;
        this.camelContextConfigurations = camelContextConfigurations;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Ignore this event if not from the root context
        if(contextRefreshedEvent.getApplicationContext().getParent() != null) {
            return;
        }

        CamelContext camelContext = contextRefreshedEvent.getApplicationContext().getBean(CamelContext.class);
        LOG.debug("Post-processing CamelContext bean: {}", camelContext.getName());
        for (RoutesBuilder routesBuilder : applicationContext.getBeansOfType(RoutesBuilder.class).values()) {
            try {
                LOG.debug("Injecting following route into the CamelContext: {}", routesBuilder);
                camelContext.addRoutes(routesBuilder);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (camelContextConfigurations != null) {
            for (CamelContextConfiguration camelContextConfiguration : camelContextConfigurations) {
                LOG.debug("CamelContextConfiguration found. Invoking: {}", camelContextConfiguration);
                camelContextConfiguration.beforeApplicationStart(camelContext);
            }
        }
        try {
            camelContext.start();
        } catch (Exception e) {
            throw new CamelSpringBootInitializationException(e);
        }
    }
}
