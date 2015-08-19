package rifi.driver.graph;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@JsonPropertyOrder("{nodes}")
public class Graph<N extends Node,E extends Edge> {

    Set<N> nodes = new LinkedHashSet<>();

    Set<E> edges = new LinkedHashSet<>();

    public void addNode(N node) {
        nodes.add(node);
    }

    public void addEdge(E edge, N source, N target) {
        source.setOutgoing(edge);
        edge.setSource(source);
        edge.setTarget(target);
        target.setIncoming(edge);
        edges.add(edge);
        nodes.add(source);
        nodes.add(target);
    }

    public Set<N> getNodes() {
        return nodes;
    }

    public void setNodes(Set<N> nodes) {
        this.nodes = nodes;
    }

    public Set<E> getEdges() {
        return edges;
    }

    public void setEdges(Set<E> edges) {
        this.edges = edges;
    }
}
