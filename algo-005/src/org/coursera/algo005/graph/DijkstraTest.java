package org.coursera.algo005.graph;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DijkstraTest {

	//private static final String RESOURCE_NAME = "org/coursera/algo005/graph/TestDijkstra.txt";	
	private static final String RESOURCE_NAME = "org/coursera/algo005/graph/dijkstraData.txt";
	private static final String COLUMN_SEPARATOR = "\t";
	private static final int SOURCE_NODE_ID = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Integer, List<NodeAndLength>> adjacencyList = Utils.readAdjacencyListFromResource(RESOURCE_NAME, COLUMN_SEPARATOR, true);
		
		Graph g = Graph.fromAdjacencyList(adjacencyList, false);
		
		Map<Node, Integer> spDistances = g.dijkstraShortestPath(SOURCE_NODE_ID);
		
		System.out.printf("SHORTEST PATH DISTANCES FROM NODE: %d\n\n", SOURCE_NODE_ID);
		for (Entry<Node, Integer> entry: spDistances.entrySet()) {
			Node node = entry.getKey();
			Integer distance = entry.getValue();
			System.out.printf("%d:\t\t%d\n", node.getNodeId(), distance);
		}
	}

	
}
