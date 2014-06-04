package org.coursera.algo005.graph;

import java.util.Arrays;
import java.util.List;

public class SccTest {

	private static final String RESOURCE_NAME = "org/coursera/algo005/graph/SCC.txt";	
	//private static final String RESOURCE_NAME = "org/coursera/algo005/graph/TestSCC.txt";
	//private static final String RESOURCE_NAME = "org/coursera/algo005/graph/kargerMinCut.txt";
	private static final String COLUMN_SEPARATOR = " ";
	//private static final String COLUMN_SEPARATOR = "\t";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Integer>> adjacencyList = Utils.readAdjacencyListFromResource(RESOURCE_NAME, COLUMN_SEPARATOR);
		
		Graph g = Graph.fromAdjacencyList(adjacencyList, true);
		
		int[] sccs = g.sccs();
		
		System.out.printf("SCCs: %s\n", Arrays.toString(sccs));
	}
}
