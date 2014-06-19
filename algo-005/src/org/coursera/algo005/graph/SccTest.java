package org.coursera.algo005.graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
		Map<Integer, List<NodeAndLength>> adjacencyList = Utils.readAdjacencyListFromResource(RESOURCE_NAME, COLUMN_SEPARATOR, false);
		
		Graph g = Graph.fromAdjacencyList(adjacencyList, true);
		
		int[] sccs = g.sccs();
		
		System.out.printf("SCCs: %s\n", Arrays.toString(sccs));
	}
}
