package com.kku.weka;

import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class DecsionTree extends BaseComputation {

	public void process() throws Exception {
		List<DataSets> dataSets = getBinaryDataSets();
		for (DataSets data : dataSets) {
			String[] trainSize = getTrainSize();
			for (int i = 0; i < trainSize.length; i++) {
				Instances[] allData = splitData(data,
						Double.parseDouble(trainSize[i]));
				Instances train = allData[0];
				Instances test = allData[1];
				Classifier cls = new LibSVM();
				cls.buildClassifier(train);

				Evaluation eval = new Evaluation(train);
				eval.evaluateModel(cls, test);
				double acc = eval.correct() / test.numInstances();
				System.out.printf("%s %s acc=%.4f\n", data.getName(),
						trainSize[i], acc);
			}
		}

	}

	public static void main(String[] args) {
		DecsionTree dt = new DecsionTree();
		try {
			dt.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
