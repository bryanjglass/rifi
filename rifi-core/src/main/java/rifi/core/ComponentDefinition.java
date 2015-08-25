package rifi.core;

import java.util.List;

public interface ComponentDefinition {
    Class getType();
    List<String> getParameters();
}
