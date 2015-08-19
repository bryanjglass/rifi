package rifi.driver.nodes;

import org.apache.camel.CamelContext;
import org.apache.camel.util.JsonSchemaHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FlowNodeDefinition {
    @Autowired
    CamelContext camelContext;

    Class type;
    String name;
    List<String> parameters = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public  List<Map<String, String>> resolveDetails() {
        String json;
        try {
            json = camelContext.getComponentParameterJsonSchema(name);
        } catch (IOException e) {
            return null;
        }
        if (json == null) {
            return null;
        }


        List<Map<String, String>> rows = JsonSchemaHelper.parseJsonSchema("properties", json, true);

        return rows;
    }

    protected Object resolveCamelComponentParameters() {
        return null;
    }
}
