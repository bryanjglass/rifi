package rifi.driver.graph;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("graph")
public class GraphController {
    @RequestMapping(name = "", method = RequestMethod.GET)
    public Graph<Node, Edge> getGraph() {
        Graph<Node, Edge> graph = new Graph<>();
        Node n1 = new Node("Node1");
        Node n2 = new Node("Node2");
        Node n3 = new Node("Node3");
        Node n4 = new Node("Node4");
        graph.addEdge(new Edge("1-2"), n1, n2);
        graph.addEdge(new Edge("1-3"), n1, n3);
        graph.addEdge(new Edge("3-4"), n3, n4);
        return graph;
    }

    @RequestMapping(name = "", method = RequestMethod.POST)
    public Graph<Node, Edge> doGraph(@RequestBody Graph<Node, Edge> graph) {
        return graph;
    }
}
