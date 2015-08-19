package rifi.driver.flows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import rifi.driver.nodes.FlowNodeDefinition;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FlowNodeRegistry {

    @Autowired
    private List<FlowNodeDefinition> flowNodes;

    @PostConstruct
    private void init() throws ClassNotFoundException {
        flowNodes = new ArrayList<>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // Filter to include only classes that have a FlowNode annotation.
        provider.addIncludeFilter(new AnnotationTypeFilter(FlowNode.class));
        // Find classes in the given package (or subpackages)
        Set<BeanDefinition> beans = provider.findCandidateComponents("rifi");
        for(BeanDefinition bean : beans) {
            Class<?> beanClas = ClassUtils.forName(bean.getBeanClassName(), ClassUtils.getDefaultClassLoader());
            FlowNode annotation = AnnotationUtils.getAnnotation(beanClas, FlowNode.class);

            FlowNodeDefinition flowNodeDefinition = new DefaultFlowNodeDefinition();
            flowNodeDefinition.setName(annotation.name());
            flowNodeDefinition.setType(beanClas);

            flowNodes.add(flowNodeDefinition);
        }

    }

    public List<FlowNodeDefinition> getFlowNodes() {
        return flowNodes;
    }

    public void setFlowNodes(List<FlowNodeDefinition> flowNodes) {
        this.flowNodes = flowNodes;
    }

    public FlowNodeDefinition findFlowNode(String name) {
        for(FlowNodeDefinition definition : flowNodes) {
            if(definition.getName().equals(name)) {
                return definition;
            }
        }

        return null;
    }
}
