package rifi.driver.flows

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import rifi.driver.nodes.FlowNode

@Component
public class FlowNodeRegistry {

    private final Map<String, FlowNode> flowNodesByName;

    @Autowired
    public FlowNodeRegistry(List<FlowNode> flowNodes) {
        flowNodesByName = flowNodes.collectEntries { FlowNode node -> return [node.name, node] }
    }

    public List<FlowNode> getFlowNodes() {
        return flowNodesByName.collect { return it.value }
    }

    public FlowNode findFlowNode(String name) {
        return flowNodesByName[name]
    }

    public List<FlowNodeDefinition> findAllFlowNodeDefinitions() {
        return flowNodesByName.collect { String name, FlowNode node ->
            return new FlowNodeDefinition(name: name, type: node.class.simpleName)
        }
    }
}
