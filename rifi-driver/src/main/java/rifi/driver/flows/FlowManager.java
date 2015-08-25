package rifi.driver.flows;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.impl.ExplicitCamelContextNameStrategy;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;
import rifi.driver.nodes.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlowManager implements ApplicationListener<ApplicationContextEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowManager.class);

    private final List<ConfigurableApplicationContext> childContexts = new ArrayList<>();

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        LOGGER.info("Application context event: " + event.toString());

        ConfigurableApplicationContext eventContext = (ConfigurableApplicationContext) event.getApplicationContext();

        if (event instanceof ContextStartedEvent) {
            childContexts.add(eventContext);
        }

        if(event instanceof ContextStoppedEvent) {
            childContexts.remove(eventContext);
        }

        LOGGER.info("Remaining application contexts {}", childContexts);
    }

    public FlowRouteBuilder startFlow(String name, rifi.driver.nodes.FlowNode root) throws Exception {
        GenericApplicationContext childApplicationContext = new GenericApplicationContext(applicationContext);
        childApplicationContext.setDisplayName(name);
        childContexts.add(childApplicationContext);

        CamelContext camelContext =  new SpringCamelContext(childApplicationContext);

        ConfigurableListableBeanFactory beanFactory = childApplicationContext.getBeanFactory();
        beanFactory.registerSingleton(camelContext.getClass().getCanonicalName(), camelContext);
        camelContext.setNameStrategy(new ExplicitCamelContextNameStrategy(name));
        childApplicationContext.refresh();

        FlowRouteBuilder routeBuilder = new FlowRouteBuilder(root);
        camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
        camelContext.addRoutes(routeBuilder);

//        camelContext.start();

        return routeBuilder;
    }

    public void stopFlow(String name) {

        ConfigurableApplicationContext context = findApplicationContextByName(name);
        if(context != null) {
            context.stop();
        }
    }

    private ConfigurableApplicationContext findApplicationContextByName(String name) {
        for(ConfigurableApplicationContext applicationContext : childContexts) {
            if(applicationContext.getDisplayName().equalsIgnoreCase(name)) {
                return applicationContext;
            }
        }

        return null;
    }
}
