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
		int nodeId = 0;

		do {
			nodeId = ++nodeCounter;
		} while (getNode(nodeId) != null);
		
		return createNode(nodeId);
	}
	
	public Node createNode(int nodeId) {
		if (getNode(nodeId) == null) {
			Node newNode = new Node(nodeId);
			
			nodeList.add(newNode);
			nodes.put(nodeId, newNode);
			
			return newNode;
		} else {
			return getNode(nodeId);
		}		
	}
	
	public Edge createEdge(Node head, Node tail) {
		int edgeId = 0;
		do {
			edgeId = ++edgeCounter;
		} while (getEdge(edgeId) != null);
		
		return createEdge(edgeId, head, tail);
	}
	
	public Edge createEdge(int edgeId, Node head, Node tail) {
		if (getEdge(edgeId) == null) {
			// avoid self-loops
			if (head == tail || head.getNodeId() == tail.getNodeId()) {
				return null;
			}
			
			Edge newEdge = new Edge(edgeId, head, tail);
			head.getIncidentEdges().put(edgeId, newEdge);
			tail.getIncidentEdges().put(edgeId, newEdge);
			
			edgeList.add(newEdge);
			edges.put(edgeId, newEdge);
			
			return newEdge;
		} else {
			return getEdge(edgeId);
		}		
	}
	
	public Node removeNode(int nodeId) {
		Node nodeToRemove = nodes.get(nodeId);
		
		return removeNode(nodeToRemove);
	}
	
	public Node removeNode(Node nodeToRemove) {
		if (nodeToRemove != null) {
			Set<Integer> incidentEdgesIds = new HashSet<Integer>(nodeToRemove.getIncidentEdges().keySet());
			for (Integer edgeId: incidentEdgesIds) {
				removeEdge(edgeId);
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
	
	public Graph clone() {
		Graph clone = new Graph();
		clone.nodeCounter = this.nodeCounter;
		clone.edgeCounter = this.edgeCounter;
		
		for (Edge edge: getEdges()) {
			Node head = edge.getHead();
			Node tail = edge.getTail();
			head = clone.createNode(head.getNodeId());
			tail = clone.createNode(tail.getNodeId());
			clone.createEdge(edge.getEdgeId(), head, tail);
						
		}
		
		return clone;
	}
	
	private void contract() {
		Edge edgeToRemove = pickRandomEdge();
		Node head = edgeToRemove.getHead();
		Node tail = edgeToRemove.getTail();
		
		// add new node to the graph, which will replace head and tail nodes
		Node mergedNode = createNode();
		// keep only edges different than head --> tail or tail --> head 
		for (Edge edge: head.getIncidentEdges().values()) {
			Node otherEndpoint = (edge.getHead() == head) ? edge.getTail() : edge.getHead();
			if (otherEndpoint != tail) {
				// keep edge
				createEdge(mergedNode, otherEndpoint);
			}
		}
		for (Edge edge: tail.getIncidentEdges().values()) {
			Node otherEndpoint = (edge.getHead() == tail) ? edge.getTail() : edge.getHead();
			if (otherEndpoint != head) {
				// keep edge
				createEdge(mergedNode, otherEndpoint);
			}
		}
		// incident edges will also be removed
		removeNode(head);
		removeNode(tail);
	}
	
	public int kargerMinCut() {		
		int minCut = getEdgeCount();
		
		if (getNodeCount() > 2) {
			long numIterations = Math.round(Math.pow(getNodeCount(), 2));
			for (long i=0; i<numIterations; i++) {
				Graph ithGraph = clone();
				while (ithGraph.getNodeCount() > 2) {
					ithGraph.contract();
				}
				
				int ithMinCut = ithGraph.getEdgeCount();
				
				System.out.printf("Iteration #%d of %d - minimum cut is: %d\n", i+1, numIterations, ithMinCut);
				
				if (ithMinCut < minCut) {
					minCut = ithMinCut;
				}
			}
		}		
		
		return minCut;
	}
	
}
