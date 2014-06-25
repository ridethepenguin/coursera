package org.coursera.algo005.median;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class MedianMaintenanceTest {

	private static final String EXERCISE_INPUT_RES_NAME = "org/coursera/algo005/median/Median.txt";
	private static final int EXERCISE_INPUT_ARRAY_SIZE = 10000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		int[] inputArray = new int[10];
//		
//		Random rng = new Random();
//		for (int i=0; i<10; i++) {
//			inputArray[i] = rng.nextInt(100);
//		}		
		
		int[] inputArray = readInputArray();
		
		MedianMaintenanceAlgorithm algo = new MedianMaintenanceAlgorithm();
		int[] medians = algo.median(inputArray);
		
		int sumTotal = 0;
		for (int median: medians) {
			sumTotal += median;
		}
		
		System.out.printf("Sum total modulo %d: %d\n", EXERCISE_INPUT_ARRAY_SIZE, sumTotal % EXERCISE_INPUT_ARRAY_SIZE);
		
//		System.out.printf("Input array was: %s\n", Arrays.toString(inputArray));
//		System.out.printf("Median array was: %s\n", Arrays.toString(median));		
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
