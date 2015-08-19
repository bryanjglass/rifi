package rifi.driver.nodes;

public interface TestableFlowNode extends FlowNode {
    Object test(Object input) throws Exception;
}
