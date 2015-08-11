package rifi.driver.flows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelHelper;
import org.apache.camel.util.LoadPropertiesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rifi.driver.nodes.*;

import java.io.IOException;
import java.util.*;
import java.util.Properties;

@RestController
@RequestMapping("flow-nodes")
public class FlowNodeController {

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("")
    public Map<String, Properties> findAvailableNodes() throws IOException, LoadPropertiesException {
        DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
        Map<String, Properties> nodes = new HashMap<>();

        nodes.putAll(defaultCamelContext.findComponents());
        nodes.putAll(defaultCamelContext.findEips());

        return nodes;
    }

    @RequestMapping("components/{nodeId}")
    public Object findComponent(@PathVariable String nodeId) throws IOException {
        DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
        return objectMapper.reader().readTree(defaultCamelContext.explainComponentJson(nodeId, true));
    }

    @RequestMapping("eips/{nodeId}")
    public Object findEip(@PathVariable String nodeId) throws IOException {
        DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
        return objectMapper.reader().readTree(defaultCamelContext.explainEipJson(nodeId, true));
    }

    @RequestMapping("endpoints/{nodeId}")
    public Object findEndpoint(@PathVariable String nodeId) throws IOException {
        DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
        return objectMapper.reader().readTree(defaultCamelContext.explainEndpointJson(nodeId + "://foo", true));
    }

    @RequestMapping("create/{type}")
    public Endpoint createRoute(@PathVariable("type") String type) throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();

        Component component = context.getComponent(type, true);
        ComponentConfiguration configuration = component.createComponentConfiguration();


        configuration.setParameters(Collections.<String, Object>emptyMap());

        Endpoint endpoint = configuration.createEndpoint();
        return endpoint;
    }

    @RequestMapping(value="route")
    public String getRoute() throws Exception {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        Node node1 = new FileNode();
        Node node2 = new FilterNode();
        Node routerNode = new RouterNode();
        Node routerPath1 = new LogNode();
        routerPath1.getOptions().put("message", "path1");
        Node routerChildPath1 = new FileNode();
        Node routerPath2 = new LogNode();
        routerPath2.getOptions().put("message", "path2");

        node1.setTarget(node2);
        node2.setSource(node1);
        node2.setTarget(routerNode);
        routerNode.setSource(node2);
        routerNode.getChildren().add(routerPath1);
        routerPath1.setTarget(routerChildPath1);
        routerChildPath1.setSource(routerPath1);
        routerNode.getChildren().add(routerPath2);

        FlowRouteBuilder routeBuilder = new FlowRouteBuilder(node1);
        routeBuilder.configure();
        return ModelHelper.dumpModelAsXml(routeBuilder.getContext(), routeBuilder.getRouteCollection().getRoutes().get(0));
    }
}
