package com.kku.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class TestNaiveBayes {
	private static String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	public static Instances loadTrain(String arffFileName) {
		try {
			String filePath = path_file + "data/arff/" + arffFileName;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes()-1);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Instance loadTest(String arffFileName) {
		try {
			String filePath = path_file + "data/arff/" + arffFileName;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes()-1);
			return data.instance(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void compute1() {
		Instances train = TestNaiveBayes.loadTrain("HeartDisease.arff");
		Instance test = TestNaiveBayes.loadTest("HeartDiseaseTest.arff");
		NaiveBayes naiveBayes = new NaiveBayes();
		try {
			naiveBayes.buildClassifier(train);
			double p = naiveBayes.classifyInstance(test);
			System.out.println(p);
			double[] dataDis = naiveBayes.distributionForInstance(test);
			System.out.println(Arrays.toString(dataDis));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Instances train = TestNaiveBayes.loadTrain("HeartDisease.arff");
		Instance test = TestNaiveBayes.loadTest("HeartDiseaseTest.arff");
		NaiveBayes naiveBayes = new NaiveBayes();
		try {
			naiveBayes.buildClassifier(train);
			double p = naiveBayes.classifyInstance(test);
			System.out.println(p);
			double[] dataDis = naiveBayes.distributionForInstance(test);
			System.out.println(Arrays.toString(dataDis));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}



}
