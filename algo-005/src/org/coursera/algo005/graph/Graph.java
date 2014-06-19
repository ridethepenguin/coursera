package org.coursera.algo005.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Graph { 
	
	private int nodeCounter = 0, edgeCounter = 0;
	private Map<Integer, Node> nodes;
	private List<Node> nodeList;
	private Map<Integer, Edge> edges;
	private List<Edge> edgeList;
	
	private boolean directed;
	private TraversalDirection traversalDirection;
	
	private Random rng;
	
	public Graph(boolean directed) {
		this.nodes = new HashMap<Integer, Node>();
		this.nodeList = new ArrayList<>();
		this.edges = new HashMap<Integer, Edge>();
		this.edgeList = new ArrayList<Edge>();
		this.directed = directed;
		this.traversalDirection = TraversalDirection.FORWARD;
		this.rng = new Random();		
	}
	
	public static Graph fromAdjacencyList(Map<Integer, List<NodeAndLength>> adjacencyList, boolean directed) {
		Graph g = new Graph(directed);
		Set<String> alreadyCreatedEdges = new HashSet<String>();
		int lastNodeCount = 0;
		
		for (Entry<Integer, List<NodeAndLength>> entry: adjacencyList.entrySet()) {			
			int headNodeId = entry.getKey();
			List<NodeAndLength> row = entry.getValue();
			if (row.size() > 1) {
				for (int i=0; i<row.size(); i++) {
					int tailNodeId = row.get(i).nodeId;
					String pairId = (headNodeId <= tailNodeId) ? (headNodeId + "-" + tailNodeId) : (tailNodeId + "-" + headNodeId);
					if (directed || !alreadyCreatedEdges.contains(pairId)) {
						Node headNode = g.createNode(headNodeId);
						Node tailNode = g.createNode(tailNodeId);
						g.createEdge(headNode, tailNode, row.get(i).length);
						alreadyCreatedEdges.add(pairId);
					} else {
						System.out.printf("Edge %s has been already created! Skipping...\n", pairId);
					}
				}
			}
			if (g.getNodeCount() % 1000 == 0 && lastNodeCount != g.getNodeCount()) {
				System.out.printf("Graph has now %d nodes and %d edges...\n", g.getNodeCount(), g.getEdgeCount());
				lastNodeCount = g.getNodeCount();
			}
		}
		
		System.out.printf("Successfully created graph with %d nodes and %d edges!\n", g.getNodeCount(), g.getEdgeCount());
		
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

	public TraversalDirection getTraversalDirection() {
		return traversalDirection;
	}

	public void setTraversalDirection(TraversalDirection traversalDirection) {
		if (traversalDirection != null) {
			this.traversalDirection = traversalDirection;
		}		
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
		return createEdge(head, tail, Edge.DEFAULT_LENGTH);
	}
	
	public Edge createEdge(int edgeId, Node head, Node tail) {
		return createEdge(edgeId, head, tail, Edge.DEFAULT_LENGTH);
	}
	
	public Edge createEdge(Node head, Node tail, int length) {
		int edgeId = 0;
		do {
			edgeId = ++edgeCounter;
		} while (getEdge(edgeId) != null);
		
		return createEdge(edgeId, head, tail, length);
	}
	
	public Edge createEdge(int edgeId, Node head, Node tail, int length) {
		if (getEdge(edgeId) == null) {
			// avoid self-loops
			if (head == tail || head.getNodeId() == tail.getNodeId()) {
				return null;
			}
			
			Edge newEdge = new Edge(edgeId, head, tail, length);
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
		Graph clone = new Graph(this.directed);
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
	
	private int dfs(Node currentNode, Node startNode, Set<Node> explored, Map<Integer, List<Node>> nodesByLeader, TreeMap<Integer, Node> finishingTimes, int currentFinishingTime, boolean firstPass) {
		explored.add(currentNode);
		
		if (!firstPass) {
			if (nodesByLeader.containsKey(startNode.getNodeId())) {
				nodesByLeader.get(startNode.getNodeId()).add(currentNode);
			} else {
				List<Node> nodeList = new ArrayList<Node>();
				nodeList.add(currentNode);
				nodesByLeader.put(startNode.getNodeId(), nodeList);			
			}
		}		
		
		for (Edge edge: currentNode.getIncidentEdges().values()) {
			if (getTraversalDirection().equals(TraversalDirection.FORWARD)) {
				if (edge.getHead().equals(currentNode) && !explored.contains(edge.getTail())) {
					currentFinishingTime = dfs(edge.getTail(), startNode, explored, nodesByLeader, finishingTimes, currentFinishingTime, firstPass);
				}
			} else { // traversalDirection == REVERSE
				if (edge.getTail().equals(currentNode) && !explored.contains(edge.getHead())) {
					currentFinishingTime = dfs(edge.getHead(), startNode, explored, nodesByLeader, finishingTimes, currentFinishingTime, firstPass);
				}
			}
		}
		
		if (firstPass) {
			currentFinishingTime++;
			
			finishingTimes.put(currentFinishingTime, currentNode);
		}
		
		return currentFinishingTime;
	}
	
	private void dfsLoop(Map<Integer, List<Node>> nodesByLeader, TreeMap<Integer, Node> finishingTimes, boolean firstPass) {
		int currentFinishingTime = 0;
		Set<Node> explored = new HashSet<Node>();
		Node startNode = null;
		Collection<Node> nodes = null;
		
		if (firstPass) {
			nodes = getNodes();
			setTraversalDirection(TraversalDirection.REVERSE);
		} else {
			nodes = finishingTimes.values();
			setTraversalDirection(TraversalDirection.FORWARD);
		}
		
		// TODO: avoid this copy!
		List<Node> nodesList = new ArrayList<>(nodes);
		
		for (int i=nodesList.size()-1; i>=0; i--) {
			Node currentNode = nodesList.get(i);
			if (!explored.contains(currentNode)) {
				startNode = currentNode;
				// System.out.printf("Launching DFS from node: %d\n", startNode.getNodeId());
				currentFinishingTime = dfs(currentNode, startNode, explored, nodesByLeader, finishingTimes, currentFinishingTime, firstPass);
			}
		}
	}
	
	public int[] sccs() {
		Map<Integer, List<Node>> nodesByLeader = new HashMap<>();
		TreeMap<Integer, Node> finishingTimes = new TreeMap<>();
		
		// first pass
		System.out.println("DFS-Loop: first pass");
		dfsLoop(nodesByLeader, finishingTimes, true);
		// second pass
		System.out.println("DFS-Loop: second pass");
		dfsLoop(nodesByLeader, finishingTimes, false);
		
		int[] sccs = new int[nodesByLeader.size()];
		int i=0;
		for (List<Node> sccMembers: nodesByLeader.values()) {
			sccs[i] = sccMembers.size();
			i++;
		}
		
		Arrays.sort(sccs);
		
		return sccs;
	}
	
	public Map<Node, Integer> dijkstraShortestPath(int sourceNodeId) {
		final int MAX_DISTANCE = 1000000;
		
		Map<Node, Integer> shortestPathDistance = new HashMap<>();
		Set<Node> visited = new HashSet<>();
		for (Node node: getNodes()) {
			if (node.getNodeId() != sourceNodeId) {
				shortestPathDistance.put(node, MAX_DISTANCE);
			} else {
				shortestPathDistance.put(node, 0);
				visited.add(node);
			}			
		}
		
		boolean end = false;
		while (!end) {
			int minLength = MAX_DISTANCE;
			Node minLengthNode = null;
			for (Node node: visited) {
				for (Edge edge: node.getIncidentEdges().values()) {
					boolean isHead = edge.getHead().equals(node);
					Node otherNode = (isHead) ? edge.getTail() : edge.getHead();
					boolean frontier = false;										
					if (directed) {
						frontier = isHead && !visited.contains(edge.getTail());
					} else {						
						frontier = !visited.contains(otherNode);
					}
					if (frontier) {
						// edge is at the frontier
						int pathLength = shortestPathDistance.get(node) + edge.getLength();
						if (pathLength < minLength) {
							minLength = pathLength;
							minLengthNode = otherNode;
						}
					}
				}
			}
			if (minLengthNode != null) {
				shortestPathDistance.put(minLengthNode, minLength);
				visited.add(minLengthNode);
				end = visited.size() == getNodeCount();
			} else {
				end = true;
			}
		}
		
		return shortestPathDistance;
	}

	public static enum TraversalDirection {
		FORWARD,
		REVERSE
	}
	
}
