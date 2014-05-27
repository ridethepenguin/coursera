package org.coursera.algo005.mincut;

public class Edge {

	private int edgeId;
	private Node head;
	private Node tail;
	
	public Edge(int edgeId, Node head, Node tail) {
		this.edgeId = edgeId;
		if (head == null || tail == null) {
			throw new IllegalArgumentException("Both head and tail node must not be null!");
		}
		this.head = head;
		this.tail = tail;
	}

	public int getEdgeId() {
		return edgeId;
	}

	public Node getHead() {
		return head;
	}

	public Node getTail() {
		return tail;
	}
	
	
}
