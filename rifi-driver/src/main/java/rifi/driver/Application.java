package rifi.driver;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.spring.boot.RoutesCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ReflectionUtils;
import rifi.driver.support.CustomRoutesCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@ComponentScan("rifi")
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }

    @Bean
    RoutesCollector routesCollector(ApplicationContext applicationContext) {
        Collection<CamelContextConfiguration> configurations = applicationContext.getBeansOfType(CamelContextConfiguration.class).values();
        return new CustomRoutesCollector(applicationContext, new ArrayList<>(configurations));
    }


}