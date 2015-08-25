package rifi.core;

import java.util.ArrayList;
import java.util.List;

public class Graph<N, E> {
    List<Node<N, E>> nodes = new ArrayList<>();
    List<Edge<N, E>> edges = new ArrayList<>();

    public List<Edge<N, E>> getEdges() {
        return edges;
    }

    public List<Node<N, E>> getNodes() {
        return nodes;
    }
}
