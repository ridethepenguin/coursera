package org.coursera.algo005.median;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianMaintenanceAlgorithm {
	
	private static final int HEAP_INITIAL_CAPACITY = 5000;

	private final class ReverseComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1.compareTo(o2) < 0) {
				return 1;
			} else if (o1.compareTo(o2) > 0) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}
	
	public int[] median(int[] inputArray) {
		PriorityQueue<Integer> lowHeap = new PriorityQueue<>(HEAP_INITIAL_CAPACITY, new ReverseComparator());
		PriorityQueue<Integer> highHeap = new PriorityQueue<>(HEAP_INITIAL_CAPACITY);
		
		int[] medians = new int[inputArray.length];
		int i=0;
		for (int element: inputArray) {
			Integer maxLow = lowHeap.peek();
			Integer minHigh = highHeap.peek();
			
			if (maxLow != null && element <= maxLow) {
				lowHeap.add(element);
			} else if (minHigh != null && element >= minHigh) {
				highHeap.add(element);
			} else {
				// cannot determine where I should put the element right now... I'll put it in the low heap,
				// and rebalance later
				lowHeap.add(element);
			}
						
			int sizeLow = lowHeap.size();
			int sizeHigh = highHeap.size();
			int diffSize = Math.abs(sizeHigh - sizeLow);
			if (diffSize > 1) {
				// rebalance!
				PriorityQueue<Integer> biggerHeap = (sizeLow < sizeHigh) ? highHeap : lowHeap;
				PriorityQueue<Integer> smallerHeap = (sizeLow < sizeHigh) ? lowHeap : highHeap;
				Integer elementToMove = biggerHeap.poll();
				smallerHeap.add(elementToMove);
			}
			
			// recalculate heap sizes
			sizeLow = lowHeap.size();
			sizeHigh = highHeap.size();
			
			int medianIdx = (((sizeLow + sizeHigh) % 2) == 0) ? (sizeLow + sizeHigh) / 2 : (sizeLow + sizeHigh + 1) / 2;
			if (sizeLow == medianIdx) {
				medians[i] = lowHeap.peek();
			} else {
				medians[i] = highHeap.peek();
			}
			
			i++;
		}
		
		return medians;
	}
	
}
