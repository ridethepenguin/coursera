package org.coursera.algo005.graph;

import java.util.List;
import java.util.Map;

public class KargerMinCutTest {

	private static final String RESOURCE_NAME = "org/coursera/algo005/graph/kargerMinCut.txt";	
	private static final String COLUMN_SEPARATOR = "\t";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Integer, List<NodeAndLength>> adjacencyList = Utils.readAdjacencyListFromResource(RESOURCE_NAME, COLUMN_SEPARATOR, false);
		
		Graph g = Graph.fromAdjacencyList(adjacencyList, false);
		
		int minCut = g.kargerMinCut();
		
		System.out.printf("MINIMUM CUT: %d\n", minCut);
	}

	
}
