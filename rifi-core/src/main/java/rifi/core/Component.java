package rifi.core;

import org.apache.camel.model.ProcessorDefinition;

import java.util.Map;

public interface Component extends ComponentBuilder {
    String getName();
    Map<String, Object> getOptions();
}
