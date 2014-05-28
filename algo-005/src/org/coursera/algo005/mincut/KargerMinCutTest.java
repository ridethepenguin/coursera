package org.coursera.algo005.mincut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KargerMinCutTest {

	private static final String RESOURCE_NAME = "org/coursera/algo005/mincut/kargerMinCut.txt";
	private static final String COLUMN_SEPARATOR = "\t";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Integer>> adjacencyList = readAdjacencyListFromResource();
		
		Graph g = Graph.fromAdjacencyList(adjacencyList);
		
		int minCut = g.kargerMinCut();
		
		System.out.printf("MINIMUM CUT: %d\n", minCut);
	}

	public static List<List<Integer>> readAdjacencyListFromResource() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_NAME);
		
		if (in != null) {
			List<List<Integer>> adjacencyList = new ArrayList<>();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			
			try {
				while ((line = reader.readLine()) != null) {
					String[] columns = line.trim().split(COLUMN_SEPARATOR);
					List<Integer> nodes = new ArrayList<>();
					for (String column: columns) {
						nodes.add(Integer.parseInt(column.trim()));
					}
					adjacencyList.add(nodes);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Exception occurred reading input from resource: " + RESOURCE_NAME, e);
			}
			
			return adjacencyList;
		} else {
			throw new IllegalArgumentException("Could not read from resource: " + RESOURCE_NAME);
		}
		
	}
}
