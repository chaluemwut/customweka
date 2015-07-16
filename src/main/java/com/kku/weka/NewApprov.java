package com.kku.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class NewApprov {
	private static int repeat_loop = 50;
	private static String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	public void process(String arffName, double dataSize) throws Exception {
		Instances allData = loadData(arffName);
		List<Double> lst = new ArrayList<>();
		for (int i = 0; i < repeat_loop; i++) {
			Classifier cls = new J48();
			// Classifier cls = new NaiveBayes();
			Instances[] data = null;
			if ("letter.p2.arff".equals(arffName)) {
				data = splitData(allData, dataSize, true);
			} else {
				data = splitData(allData, dataSize, false);
			}

			Instances training = data[0];
			Instances test = data[1];
			cls.buildClassifier(training);

			Evaluation eTest = new Evaluation(training);
			eTest.evaluateModel(cls, test);
			double acc = eTest.correct() / test.numInstances();
			lst.add(acc);
		}
		DescriptiveStatistics desc = new DescriptiveStatistics(lst.size());
		for (Double d : lst) {
			desc.addValue(d);
		}
		System.out.println("mean " + desc.getMean());
	}

	public Instances loadData(String arffFileName) {
		try {
			String filePath = path_file + "data/arff/" + arffFileName;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			Random rn = new Random();
			data.randomize(new Random(rn.nextInt()));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Instances[] splitData(Instances data, double trainingDataSize,
			boolean clsAtFirst) throws IOException {
		Random rn = new Random();
		data.randomize(new Random(rn.nextInt()));
		int n = data.numInstances();
		int trainingEnd = (int) Math.floor(n * trainingDataSize);
		Instances training = new Instances(data, 0, trainingEnd);
		int classIndex = training.numAttributes() - 1;
		if (clsAtFirst) {
			classIndex = 0;
		}
		training.setClassIndex(classIndex);
		Instances testing = new Instances(data, trainingEnd - 1, n
				- trainingEnd);
		testing.setClassIndex(classIndex);
		return new Instances[] { training, testing };
	}

	public static void main(String[] args) {
		NewApprov obj = new NewApprov();
		try {
			String dataSetName = "adult.arff";
			System.out.println("********* adult *************");
			obj.process(dataSetName, 0.25);
			obj.process(dataSetName, 0.50);
			obj.process(dataSetName, 0.75);
			System.out.println("********** HeartDiseaseBinary ************");
			dataSetName = "HeartDiseaseBinary.arff";
			obj.process(dataSetName, 0.25);
			obj.process(dataSetName, 0.50);
			obj.process(dataSetName, 0.75);
			System.out.println("*********** letter ***********");
			dataSetName = "letter.p2.arff";
			obj.process(dataSetName, 0.25);
			obj.process(dataSetName, 0.50);
			obj.process(dataSetName, 0.75);
			System.out.println("********** australian ************");
			dataSetName = "statlog/australian.arff";
			obj.process(dataSetName, 0.25);
			obj.process(dataSetName, 0.50);
			obj.process(dataSetName, 0.75);
			System.out.println("*********** german ***********");
			dataSetName = "statlog/german.arff";
			obj.process(dataSetName, 0.25);
			obj.process(dataSetName, 0.50);
			obj.process(dataSetName, 0.75);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
