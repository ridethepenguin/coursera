package org.coursera.algo005.hash;

import java.util.Arrays;
import java.util.HashSet;

public class TwoSumAlgorithm {

	public int[] twoSum(long[] inputArray, long[] targets) {
		int[] count = new int[targets.length];
		Arrays.fill(count, 0);
		
		HashSet<Long> inputElements = new HashSet<>();
		// insert array elements into hash set
		for (long element: inputArray) {
			inputElements.add(element);
		}
		
		for (int i=0; i<targets.length; i++) {
			long target = targets[i];					
			
			// count pairs of distinct numbers summing up to target
			for (long element: inputArray) {
				long complementary = target - element;				
				if (inputElements.contains(complementary) && element != complementary) {
					count[i]++;
					break;
				}				
			}
			
			if (i % 1000 == 0) {
				System.out.printf("Tested %d out of %d target values...\n", i+1, targets.length);
			}
		}		
		
		return count;
	}
	
}
