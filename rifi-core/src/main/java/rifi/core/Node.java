package rifi.core;

public class Node<N, E> {
    N data;
    Edge<N, E> incoming;
    Edge<N, E> outgoing;

    public Node(N data) {
        this.data = data;
    }

    public N getData() {
        return data;
    }

    public Edge<N, E> getIncoming() {
        return incoming;
    }

    public Edge<N, E> getOutgoing() {
        return outgoing;
    }
}
