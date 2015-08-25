package rifi.core;

import org.apache.camel.model.ProcessorDefinition;

public interface ComponentBuilder {
    ProcessorDefinition build(Context context, ProcessorDefinition source) throws Exception;
}
