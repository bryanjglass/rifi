package rifi.core;

public class Edge<N, E> {
    E data;
    Node<N, E> source;
    Node<N, E> target;

    public Edge(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public Node<N, E> getSource() {
        return source;
    }

    public Node<N, E> getTarget() {
        return target;
    }
}
