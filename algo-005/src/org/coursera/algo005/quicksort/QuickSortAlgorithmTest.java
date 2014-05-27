package org.coursera.algo005.quicksort;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class QuickSortAlgorithmTest {

	private static final String EXERCISE_INPUT_RES_NAME = "QuickSort.txt";
	private static final int EXERCISE_INPUT_ARRAY_SIZE = 10000;
	private static final int RANDOM_INPUT_ARRAY_SIZE = 100;
	private static final int[] TEST_ARRAY = { 6, 3, 1, 2, 4, 5};
	
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.err.printf("USAGE: " + QuickSortAlgorithm.class.getName() + " <exercise|test|random>\n");
			System.exit(-1);
		}
		
		String runType = args[0];
		int[] inputArray = null;
		if (runType.toLowerCase().equals("exercise")) {
			inputArray = readInputArray();
		} else if (runType.toLowerCase().equals("test")) {
			inputArray =  TEST_ARRAY;
		} else if (runType.toLowerCase().equals("random")) {
			inputArray =  generateInputArray();
		} else {
			throw new IllegalArgumentException("Invalid option: " + runType);
		}
		
		QuickSortAlgorithm qs1 = new QuickSortAlgorithm(QuickSortAlgorithm.STRATEGY_FIRST_ELEMENT);
		QuickSortAlgorithm qs2 = new QuickSortAlgorithm(QuickSortAlgorithm.STRATEGY_LAST_ELEMENT);
		QuickSortAlgorithm qs3 = new QuickSortAlgorithm(QuickSortAlgorithm.STRATEGY_MEDIAN_OF_THREE);
		QuickSortAlgorithm[] qsAlgorithms = { qs1, qs2, qs3 };
		
		int[] input1 = Arrays.copyOf(inputArray, inputArray.length);
		int[] input2 = Arrays.copyOf(inputArray, inputArray.length);
		int[] input3 = Arrays.copyOf(inputArray, inputArray.length);
		int[][] inputArrays = { input1, input2, input3 };
		
//		System.out.printf("INPUT ARRAY: %s\n", Arrays.toString(inputArrays[0]));
		for (int i=0; i<qsAlgorithms.length; i++) {
			int res = qsAlgorithms[i].doQuicksort(inputArrays[i]);
			
//			System.out.printf("SORTED ARRAY: %s\n", Arrays.toString(inputArrays[i]));
			System.out.printf("COMPARISONS: %d\n", res);
			System.out.printf("-------------------\n");
		}
		
	}
	
	private static int[] generateInputArray() {
		Random rng = new Random();
		
		final int[] testArray = new int[RANDOM_INPUT_ARRAY_SIZE];
		
		for (int i=0; i<testArray.length; i++) {
			testArray[i] = rng.nextInt(100 * RANDOM_INPUT_ARRAY_SIZE);
		}
		
		return testArray;
	}
	
	private static int[] readInputArray() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(EXERCISE_INPUT_RES_NAME);
		if (in != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int[] inputArray = new int[EXERCISE_INPUT_ARRAY_SIZE];
			int row = 0;
			try {
				while ((line = reader.readLine()) != null) {
					inputArray[row] = Integer.parseInt(line.trim());
					row++;
				}
				
				return inputArray;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Could not find resource: " + EXERCISE_INPUT_RES_NAME);
		}
	}
	
}
