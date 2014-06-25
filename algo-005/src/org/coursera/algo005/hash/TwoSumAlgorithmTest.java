package org.coursera.algo005.hash;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TwoSumAlgorithmTest {

	private static final String EXERCISE_INPUT_RES_NAME = "org/coursera/algo005/hash/algo1-programming_prob-2sum.txt";
	private static final int EXERCISE_INPUT_ARRAY_SIZE = 1000000;
	private static final int MIN_TARGET = -10000;
	private static final int MAX_TARGET = 10000;
	
	public static void main(String[] args) {
		long[] inputArray = readInputArray();
		long[] targets = new long[MAX_TARGET-MIN_TARGET+1];
		int i=0;
		for (long target=MIN_TARGET; target<=MAX_TARGET; i++, target++) {
			targets[i] = target;
		}
		
		TwoSumAlgorithm algo = new TwoSumAlgorithm();
		int[] results = algo.twoSum(inputArray, targets);
		int gtZero = 0;
		for (int result: results) {
			if (result > 0) {
				gtZero++;
			}
		}
			
//			if ((target - MIN_TARGET) % 1000 == 0) {
//				System.out.printf("Tested %d out of %d target values...\n", (target - MIN_TARGET) / 1000, MAX_TARGET - MIN_TARGET);
//			}
		
		System.out.printf("Number of target values t for which exists a pair of numbers x, y such that x + y = t: %d\n", gtZero);
	}

	private static long[] readInputArray() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(EXERCISE_INPUT_RES_NAME);
		if (in != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			long[] inputArray = new long[EXERCISE_INPUT_ARRAY_SIZE];
			int row = 0;
			try {
				while ((line = reader.readLine()) != null) {
					inputArray[row] = Long.parseLong(line.trim());
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
