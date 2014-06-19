package org.coursera.algo005.graph;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {	
	
	public static Map<Integer, List<NodeAndLength>> readAdjacencyListFromResource(String resourceName, String columnSeparator, boolean withLength) {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		
		if (in != null) {
			Map<Integer, List<NodeAndLength>> adjacencyList = new HashMap<>();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int i=0;
			
			try {
				while ((line = reader.readLine()) != null) {
					String[] columns = line.trim().split(columnSeparator);
					List<NodeAndLength> nodes = new ArrayList<>();
					int j=0, headNodeId=0;
					for (String column: columns) {
						if (j > 0) {
							int nodeId, length;
							if (withLength) {
								String[] nodeAndLength = column.trim().split(",");
								if (nodeAndLength.length != 2) {
									String errMsg = String.format("Wrong input format at row %d and column %d: %s", i+1, j+1, column);
									throw new IllegalArgumentException(errMsg);
								}
								nodeId = Integer.parseInt(nodeAndLength[0]);
								length = Integer.parseInt(nodeAndLength[1]);
							} else {
								nodeId = Integer.parseInt(column.trim());
								length = Edge.DEFAULT_LENGTH;
							}
							nodes.add(new NodeAndLength(nodeId, length));
						} else {
							headNodeId = Integer.parseInt(column.trim());
						}
						j++;
					}
					adjacencyList.put(headNodeId, nodes);
					i++;
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
