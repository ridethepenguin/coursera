package org.coursera.algo005.quicksort;


public class QuickSortAlgorithm {

	public static final int STRATEGY_FIRST_ELEMENT = 1;
	public static final int STRATEGY_LAST_ELEMENT = 2;
	public static final int STRATEGY_MEDIAN_OF_THREE = 3;
	
	private int numComparisons = 0;
	private PivotStrategy pivotStrategy = null;
	
	public QuickSortAlgorithm(final int strategyType) {
		pivotStrategy = PivotStrategyFactory.create(strategyType);
	}
	
	/**
	 * Swaps two elements of the provided array.
	 *  
	 * @param inputArray
	 * @param sourceIdx
	 * @param destIdx
	 */
	private void swap(int[] inputArray, final int sourceIdx, final int destIdx) {
		if (sourceIdx != destIdx) {
			int temp = inputArray[destIdx];
			inputArray[destIdx] = inputArray[sourceIdx];
			inputArray[sourceIdx] = temp;
		}		
	}
	
	/**
	 * This implementation of the partition subroutine follows exactly the same procedure
	 * outlined in the course lectures. 
	 * 
	 * @param inputArray
	 * @param startIdx
	 * @param endIdx
	 */
	private int partition(int[] inputArray, final int pivotIdx, final int startIdx, final int endIdx) {
		// move the pivot element to the beginning of the array
		swap(inputArray, pivotIdx, startIdx);
		final int PIVOT_IDX = startIdx;
		int i = PIVOT_IDX; // the index of the last element less than the pivot
		int j = PIVOT_IDX + 1; // the index of the first unknown element
		
		while (j<endIdx) {
			// compare k-th element with the pivot
			if (inputArray[j] <= inputArray[PIVOT_IDX]) {
				// visited element is less than the pivot: move element next to the last element smaller than the pivot 
				i++;
				swap(inputArray, j, i);
				// move j forward;
				j++;
			} else {
				// visited element is greater than the pivot: do nothing, just move j forward
				j++;				
			}
		}
		
		// position the pivot element
		swap(inputArray, i, PIVOT_IDX);
		
		// increase comparisons count
		numComparisons += endIdx-startIdx-1;
		
		// return the new pivot index
		return i;
	}
	
	private void quicksort(int[] inputArray, final int startIdx, final int endIdx) {
		int length = endIdx - startIdx;
		
		if (length == 1) {
			return;
		} else {
			int pivotIdx = pivotStrategy.choosePivot(inputArray, startIdx, endIdx);
			pivotIdx = partition(inputArray, pivotIdx, startIdx, endIdx);
			
			// recurse on left subarray
			int leftStartIdx = startIdx;
			int leftEndIdx = pivotIdx;
			if (leftStartIdx < leftEndIdx) {				
				quicksort(inputArray, leftStartIdx, leftEndIdx);
			}
			// recurse on right subarray
			int rightStartIdx = pivotIdx+1;
			int rightEndIdx = endIdx;
			if (rightStartIdx < rightEndIdx) {
				quicksort(inputArray, rightStartIdx, rightEndIdx);
			}
		}
	}
	
	/**
	 * Sorts the input array in-place and returns the number of comparisons used to sort it.
	 * 
	 * @param inputArray
	 * @return The number of comparisons used to sort the input array
	 */
	public int doQuicksort(int[] inputArray) {
		if (inputArray == null || inputArray.length == 0) {
			throw new IllegalArgumentException("Input array must contain at least one element!");
		}
		
		numComparisons = 0;
		
		quicksort(inputArray, 0, inputArray.length);
		
		return numComparisons;
	}
	
	
	private interface PivotStrategy {
		
		public int choosePivot(int[] inputArray, final int startIdx, final int endIdx);
		
	}
	
	private static class FirstElementPivotStrategy implements PivotStrategy {
		
		@Override
		public int choosePivot(int[] inputArray, int startIdx, int endIdx) {
			return startIdx;
		}
		
	}
	
	private static class LastElementPivotStrategy implements PivotStrategy {
		
		@Override
		public int choosePivot(int[] inputArray, int startIdx, int endIdx) {
			return endIdx-1;
		}
		
	}
	
	private static class MedianOfThreePivotStrategy implements PivotStrategy {
		
		@Override
		public int choosePivot(int[] inputArray, int startIdx, int endIdx) {
			int length = endIdx-startIdx;
			
//			System.out.printf("Array length: %d\n", length);
			
			final int FIRST_IDX = startIdx;
			final int LAST_IDX = endIdx-1;
			final int MIDDLE_IDX = startIdx + ((length - 1) / 2);			
			
			final int firstEl = inputArray[FIRST_IDX];
			final int lastEl = inputArray[LAST_IDX];
			final int middleEl = inputArray[MIDDLE_IDX];
			
//			System.out.printf("First element index: %d\t\tFirst element: %d\n", FIRST_IDX, firstEl);
//			System.out.printf("Last element index: %d\t\tLast element: %d\n", LAST_IDX, lastEl);
//			System.out.printf("Middle element index: %d\t\tMiddle element: %d\n", MIDDLE_IDX, middleEl);

			int medianIdx = -1;
			int[] discardedIdxs = new int[2];
			if (firstEl <= middleEl) {
				if (middleEl <= lastEl) {
					medianIdx = MIDDLE_IDX;
					discardedIdxs[0] = FIRST_IDX;
					discardedIdxs[1] = LAST_IDX;
				} else { // middleEl > lastEl					
					if (firstEl <= lastEl) {
						medianIdx = LAST_IDX;
						discardedIdxs[0] = FIRST_IDX;
						discardedIdxs[1] = MIDDLE_IDX;
					} else { // firstEl > lastEl						
						medianIdx = FIRST_IDX;
						discardedIdxs[0] = LAST_IDX;
						discardedIdxs[1] = MIDDLE_IDX;
					}
				}
			} else { // firstEl > middleEl
				if (firstEl <= lastEl) {
					medianIdx = FIRST_IDX;
					discardedIdxs[0] = LAST_IDX;
					discardedIdxs[1] = MIDDLE_IDX;
				} else { // firstEl > lastEl
					if (middleEl <= lastEl) {
						medianIdx = LAST_IDX;
						discardedIdxs[0] = FIRST_IDX;
						discardedIdxs[1] = MIDDLE_IDX;
					} else { // middleEl > lastEl
						medianIdx = MIDDLE_IDX;
						discardedIdxs[0] = FIRST_IDX;
						discardedIdxs[1] = LAST_IDX;
					}
				}
			}
			
			// sanity check
			if (!((inputArray[medianIdx] <= inputArray[discardedIdxs[0]] && inputArray[medianIdx] >= inputArray[discardedIdxs[1]]) ||
					(inputArray[medianIdx] <= inputArray[discardedIdxs[1]] && inputArray[medianIdx] >= inputArray[discardedIdxs[0]]))) {
				String errMsg = String.format("Wrong pivot choice! Chose %d, other candidates were %d and %d", inputArray[medianIdx], inputArray[discardedIdxs[0]], inputArray[discardedIdxs[1]]);
				throw new RuntimeException(errMsg);
			}
			
			return medianIdx;
		}
		
	}
	
	private static class PivotStrategyFactory {
		
		public static PivotStrategy create(final int strategyType) {
			switch (strategyType) {
				case STRATEGY_FIRST_ELEMENT:
					return new FirstElementPivotStrategy();
				case STRATEGY_LAST_ELEMENT:
					return new LastElementPivotStrategy();
				case STRATEGY_MEDIAN_OF_THREE:
					return new MedianOfThreePivotStrategy();
				default:
					throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
			}
		}
		
	}
}
