package org.coursera.algo005.graph;

import java.util.List;

public class KargerMinCutTest {

	private static final String RESOURCE_NAME = "org/coursera/algo005/graph/kargerMinCut.txt";	
	private static final String COLUMN_SEPARATOR = "\t";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Integer>> adjacencyList = Utils.readAdjacencyListFromResource(RESOURCE_NAME, COLUMN_SEPARATOR);
		
		Graph g = Graph.fromAdjacencyList(adjacencyList, false);
		
		int minCut = g.kargerMinCut();
		
		System.out.printf("MINIMUM CUT: %d\n", minCut);
	}

	
}
