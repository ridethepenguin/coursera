package org.coursera.algo005.mincut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
	
	private int nodeCounter = 0, edgeCounter = 0;
	private Map<Integer, Node> nodes;
	private List<Node> nodeList;
	private Map<Integer, Edge> edges;
	private List<Edge> edgeList;
	private Random rng;
	
	public Graph() {
		this.nodes = new HashMap<Integer, Node>();
		this.nodeList = new ArrayList<>();
		this.edges = new HashMap<Integer, Edge>();
		this.edgeList = new ArrayList<Edge>();
		this.rng = new Random();
	}
	
	public static Graph fromAdjacencyList(List<List<Integer>> adjacencyList) {
		Graph g = new Graph();
		Set<String> alreadyCreatedEdges = new HashSet<String>();
		
		for (List<Integer> row: adjacencyList) {
			int headNodeId = row.get(0);
			if (row.size() > 1) {
				for (int i=1; i<row.size(); i++) {
					int tailNodeId = row.get(i);
					String pairId = (headNodeId <= tailNodeId) ? (headNodeId + "-" + tailNodeId) : (tailNodeId + "-" + headNodeId);
					if (!alreadyCreatedEdges.contains(pairId)) {
						Node headNode = g.createNode(headNodeId);
						Node tailNode = g.createNode(tailNodeId);
						g.createEdge(headNode, tailNode);
						alreadyCreatedEdges.add(pairId);
					}
				}
			}
		}
		
		return g;
	}
	
	public int getNodeCount() {
		return nodeList.size();
	}
	
	public int getEdgeCount() {
		return edgeList.size();
	}
	
	public Collection<Node> getNodes() {
		return new HashSet<Node>(nodeList);
	}
	
	public Node getNode(int nodeId) {
		return nodes.get(nodeId);
	}
	
	public Collection<Edge> getEdges() {
		return new HashSet<Edge>(edgeList);
	}
	
	public Edge getEdge(int edgeId) {
		return edges.get(edgeId);
	}

	public Node createNode() {
		int nodeId = ++nodeCounter;
		
		return createNode(nodeId);
	}
	
	public Node createNode(int nodeId) {
		if (getNode(nodeId) == null) {
			Node newNode = new Node(nodeId);
			
			nodeList.add(newNode);
			
			return nodes.put(nodeId, newNode);
		} else {
			return getNode(nodeId);
		}		
	}
	
	public Edge createEdge(Node head, Node tail) {
		// avoid self-loops
		if (head == tail || head.getNodeId() == tail.getNodeId()) {
			return null;
		}
		
		int edgeId = ++edgeCounter;
		
		Edge newEdge = new Edge(edgeId, head, tail);
		head.getIncidentEdges().put(edgeId, newEdge);
		tail.getIncidentEdges().put(edgeId, newEdge);
		
		edgeList.add(newEdge);
		
		return edges.put(edgeId, newEdge);
	}
	
	public Node removeNode(int nodeId) {
		Node nodeToRemove = nodes.get(nodeId);
		
		return removeNode(nodeToRemove);
	}
	
	public Node removeNode(Node nodeToRemove) {
		if (nodeToRemove != null) {			
			for (Edge edge: nodeToRemove.getIncidentEdges().values()) {
				removeEdge(edge.getEdgeId());
			}
			nodeList.remove(nodeToRemove);
			nodes.remove(nodeToRemove.getNodeId());
		}
		
		return nodeToRemove;
	}
	
	public Edge removeEdge(int edgeId) {
		Edge edgeToRemove = edges.get(edgeId);
		
		return removeEdge(edgeToRemove);
	}
	
	public Edge removeEdge(Edge edgeToRemove) {
		if (edgeToRemove != null) {
			edgeToRemove.getHead().removeEdge(edgeToRemove.getEdgeId());
			edgeToRemove.getTail().removeEdge(edgeToRemove.getEdgeId());
			edgeList.remove(edgeToRemove);
			edges.remove(edgeToRemove.getEdgeId());
		}
		
		return edgeToRemove;
	}
	
	public Node pickRandomNode() {
		int randomIdx = rng.nextInt(getNodeCount());
		
		return nodeList.get(randomIdx);
	}
	
	public Edge pickRandomEdge() {
		int randomIdx = rng.nextInt(getEdgeCount());
		
		return edgeList.get(randomIdx);
	}
	
}
