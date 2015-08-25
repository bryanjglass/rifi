package rifi.driver.flows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.model.ModelHelper;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.util.LoadPropertiesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rifi.driver.nodes.*;
import rifi.driver.nodes.FlowNode;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Properties;

@RestController
@RequestMapping("flow-nodes")
public class FlowNodeController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FlowNodeRegistry flowNodeRegistry;

    @Autowired
    FlowManager flowManager;

    @Autowired
    CamelContext camelContext;

    @Autowired
    ApplicationContext applicationContext;

    public Map<String, Properties> findAvailableNodes() throws IOException, LoadPropertiesException {
        Map<String, Properties> nodes = new HashMap<>();

        nodes.putAll(camelContext.findComponents());
        nodes.putAll(camelContext.findEips());

        return nodes;
    }

    @RequestMapping("")
    public Collection<FlowNodeDefinition> findAllFlowNodeDefinitions() {
        return flowNodeRegistry.findAllFlowNodeDefinitions();
    }

    @RequestMapping("nodes/{nodeId}")
    public  List<Map<String, String>> findFlowNode(@PathVariable("nodeId") String nodeId) {
        FlowNode flowNode = flowNodeRegistry.findFlowNode(nodeId);

        if(flowNode == null) {
            throw new EntityNotFoundException();
        }

        return null;
    }

    @RequestMapping("components/{nodeId}")
    public Object findComponent(@PathVariable String nodeId) throws IOException {
        return objectMapper.reader().readTree(camelContext.explainComponentJson(nodeId, true));
    }

    @RequestMapping("eips/{nodeId}")
    public Object findEip(@PathVariable String nodeId) throws IOException {
        return objectMapper.reader().readTree(camelContext.explainEipJson(nodeId, true));
    }

    @RequestMapping("endpoints/{nodeId}")
    public Object findEndpoint(@PathVariable String nodeId) throws IOException {
        return objectMapper.reader().readTree(camelContext.explainEndpointJson(nodeId + "://foo", true));
    }

    @RequestMapping("create/{type}")
    public Endpoint createRoute(@PathVariable("type") String type) throws Exception {
        Component component = camelContext.getComponent(type, true);
        ComponentConfiguration configuration = component.createComponentConfiguration();


        configuration.setParameters(Collections.<String, Object>emptyMap());

        Endpoint endpoint = configuration.createEndpoint();
        return endpoint;
    }


    @RequestMapping(value="route/start/{name}")
    public String getRoute(@PathVariable("name") String name) throws Exception {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        FlowNode rootNode = createGraph();
        RouteBuilder routeBuilder = flowManager.startFlow(name, rootNode);

        return ModelHelper.dumpModelAsXml(routeBuilder.getContext(), routeBuilder.getRouteCollection());
    }

    @RequestMapping("route/stop/{name}")
    public void stopRoute(@PathVariable("name") String name) {
        flowManager.stopFlow(name);
    }

    @RequestMapping(value = "graph")
    public FlowNode getGraph() {
        return createGraph();
    }

    private FlowNode createGraph() {
        FlowNode node1 = new TimerFlowNode();
        node1.getOptions().put("name", "myTimer");
        node1.getOptions().put("period", 5000);
        FlowNode node2 = new FileFlowNode();
        node2.getOptions().put("directoryName", "/tmp/first");
        FlowNode routerNode = new RouterFlowNode();
        FlowNode routerPath1 = new LogFlowNode();
        routerPath1.getOptions().put("message", "path1");
        FlowNode routerChildPath1 = new FileFlowNode();
        FlowNode routerPath2 = new LogFlowNode();
        routerPath2.getOptions().put("message", "path2");
        FlowNode lastNode = new FileFlowNode();
        lastNode.getOptions().put("directoryName", "/tmp/last");

        node1.setTargetNode(node2);
        node2.setSourceNode(node1);
        node2.setTargetNode(routerNode);
        routerNode.setSourceNode(node2);
        routerNode.getChildNodes().add(routerPath1);
        routerPath1.setSourceNode(routerNode);
        routerPath1.setTargetNode(routerChildPath1);
        routerChildPath1.setSourceNode(routerPath1);
        routerChildPath1.setSourceNode(routerPath1);
        routerNode.getChildNodes().add(routerPath2);
        routerPath2.setSourceNode(routerNode);
        routerNode.setTargetNode(lastNode);
        lastNode.setSourceNode(routerNode);

        return node1;
    }

    @RequestMapping("test")
    public Object test() {
        TestableFlowNode testNode = new FileFlowNode();
        testNode.getOptions().put("directoryName", "/tmp/last");
        try {
            return testNode.test(null);
        } catch (Exception e) {
            return null;
        }
    }
}
