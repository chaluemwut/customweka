package com.kku.weka;

import org.apache.commons.math3.distribution.NormalDistribution;

public class TestCompute {

	public static void main(String[] args) {
		String str = "95,48,83,178,72,10,162,42,20,159,176,379,184,70,6,16,187,197,1";
		String[] strList = str.split(",");
		System.out.println(strList.length);
		// double[][] data = new double[][]{
		// {52.5512, 9.4937, 63.0},
		// {0.561, 0.4963, 1.0},
		// {2.7927, 0.9138, 1.0},
		// {129.1364, 16.212, 145.0},
		// {242.5944, 53.2799, 233.0},
		// {0.1402, 0.3472,1.0},
		// {0.8354, 0.9833, 2.0},
		// {158.3272, 19.1147, 150.0},
		// {0.1402, 0.3472, 0},
		// {0.5903, 0.7754, 2.3},
		// {1.4085, 0.5928, 3.0},
		// {0.2733, 0.6304, 0.0},
		// {4.7607, 1.5222,6.0}
		// };
		// double result = 1;
		// for(int i=0;i<13;i++){
		// double mean = data[i][0];
		// double sd = data[i][1];
		// double iData = data[i][2];
		// NormalDistribution dObj = new NormalDistribution(mean, sd);
		// double den = dObj.density(iData);
		// System.out.println(den);
		// result = result*den;
		// }
		// System.out.printf("%.4f", result);
	}
}
