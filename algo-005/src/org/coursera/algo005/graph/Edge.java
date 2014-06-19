package org.coursera.algo005.graph;

public class Edge {

	static final int DEFAULT_LENGTH = 1;
	
	private int edgeId;
	private Node head;
	private Node tail;
	private int length;
	
	public Edge(int edgeId, Node head, Node tail) {
		this(edgeId, head, tail, DEFAULT_LENGTH);
	}
	
	public Edge(int edgeId, Node head, Node tail, int length) {
		this.edgeId = edgeId;
		if (head == null || tail == null) {
			throw new IllegalArgumentException("Both head and tail node must not be null!");
		}
		this.head = head;
		this.tail = tail;
		this.length = length;
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
	
	public int getLength() {
		return length;
	}
	
}
