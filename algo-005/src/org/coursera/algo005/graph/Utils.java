package org.coursera.algo005.graph;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {	
	
	public static List<List<Integer>> readAdjacencyListFromResource(String resourceName, String columnSeparator) {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		
		if (in != null) {
			List<List<Integer>> adjacencyList = new ArrayList<>();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			
			try {
				while ((line = reader.readLine()) != null) {
					String[] columns = line.trim().split(columnSeparator);
					List<Integer> nodes = new ArrayList<>();
					for (String column: columns) {
						nodes.add(Integer.parseInt(column.trim()));
					}
					adjacencyList.add(nodes);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Exception occurred reading input from resource: " + resourceName, e);
			}
			
			return adjacencyList;
		} else {
			throw new IllegalArgumentException("Could not read from resource: " + resourceName);
		}
		
	}
	
}
