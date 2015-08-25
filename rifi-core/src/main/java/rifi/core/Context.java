package rifi.core;

import org.apache.camel.builder.RouteBuilder;

public class Context {

    RouteBuilder routeBuilder;

    public RouteBuilder getRouteBuilder() {
        return this.routeBuilder;
    }

    public void addBean(Class type) {
        System.out.println("Added "+type.getName());
    }
}
