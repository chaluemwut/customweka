package com.kku.customnb;

import weka.core.Instance;
import weka.core.Instances;

import com.kku.weka.BaseComputation;

public class ReadData extends BaseComputation {

	public static void main(String[] args) {
		ReadData r = new ReadData();
		Instances data = r.loadData("letter.p2.arff");
		String str = "[";
		for (int i = 0; i < data.numInstances(); i++) {
			Instance ins = data.instance(i);
			double v = ins.value(2);
			str += " " + v;
		}
		str += "]";
		System.out.println(str);
	}

}
