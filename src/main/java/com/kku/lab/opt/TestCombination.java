package com.kku.lab.opt;

import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.Arrays;
import java.util.List;

public class TestCombination {

	public static void main(String[] args) {
		List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		PermutationIterator<List<Integer>> lstData = new PermutationIterator(
				lst);
		while (lstData.hasNext()) {
			List<List<Integer>> lsti = lstData.next();
			System.out.println(lsti);
		}
		// System.out.println(String.format("%d", (int) Math.pow(9, 5)));
		// Combinations cmb = new Combinations(9, 9);
		// Iterator<int[]> it = cmb.iterator();
		// for (int[] is : cmb) {
		// System.out.println(Arrays.toString(is));
		// }
	}
}
