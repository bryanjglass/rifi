package rifi.driver.nodes;

import org.springframework.stereotype.Component;

@Component
public class LogFlowNodeDefinition extends FlowNodeDefinition {
    public LogFlowNodeDefinition() {
        this.type = LogFlowNode.class;
        this.name = "log";
    }
}
