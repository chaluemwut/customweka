package com.kku.math;

import org.apache.commons.math3.distribution.BinomialDistribution;

public class TestMath {

	public static void main(String[] args) {
		BinomialDistribution b = new BinomialDistribution(5, 0.167);
		double p = b.probability(2);
		System.out.println(p);
	}

}
