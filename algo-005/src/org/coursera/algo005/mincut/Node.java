package org.coursera.algo005.mincut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

	private int nodeId;
	private Map<Integer, Edge> incidentEdges;
	
	public Node(int nodeId) {
		this.nodeId = nodeId;
		this.incidentEdges = new HashMap<Integer, Edge>();
	}
	
	public Node(int nodeId, List<Edge> edges) {
		this(nodeId);
		if (edges != null) {
			for (Edge edge: edges) {
				incidentEdges.put(edge.getEdgeId(), edge);
			}
		}
	}

	public int getNodeId() {
		return nodeId;
	}

	public Map<Integer, Edge> getIncidentEdges() {
		return incidentEdges;
	}

	public Edge removeEdge(int edgeId) {
		return incidentEdges.remove(edgeId);
	}
	
}
