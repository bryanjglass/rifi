package rifi.driver.nodes;

import org.springframework.stereotype.Component;

@Component
public class FileFlowNodeDefinition extends FlowNodeDefinition {

    public FileFlowNodeDefinition() {
        this.name = "file";
        this.type = FileFlowNode.class;
    }
}
