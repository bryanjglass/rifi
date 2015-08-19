package rifi.driver.support;

import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.spring.boot.RoutesCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomRoutesCollector extends RoutesCollector {
    public CustomRoutesCollector() {
        super(null, null);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
