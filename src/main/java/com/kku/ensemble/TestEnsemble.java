package com.kku.ensemble;

import weka.classifiers.trees.J48;
import weka.core.Instances;

import com.kku.util.LoaderUtil;

public class TestEnsemble {

	public static void main(String[] args) {
		Instances data = LoaderUtil.load("HeartDiseaseBinary.arff");
		data.setClassIndex(data.numAttributes() - 1);
		J48 cls = new J48();
		try {
			cls.buildClassifier(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
