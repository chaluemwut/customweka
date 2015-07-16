package com.kku.util;

import weka.core.Instance;
import weka.core.Instances;

public class WekaUtil {

	public static double[] getY(Instances insList) {
		double[] d = new double[insList.numInstances()];
		for (int i = 0; i < insList.numInstances(); i++) {
			Instance ins = insList.instance(i);
			int clsIndex = ins.classIndex();
			d[i] = ins.value(clsIndex);
		}
		return d;
	}

}
