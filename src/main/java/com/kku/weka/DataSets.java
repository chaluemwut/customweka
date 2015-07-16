package com.kku.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class DataSets {
	private static String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	private Instances training;
	private Instances testing;
	private double trainingDataSize;
	private boolean isFirstColumnPredict = false;
	private Instances data;
	
	private String name;

	public DataSets() {
	}

	public void loader() throws IOException {
		Random rn = new Random();
		data.randomize(new Random(rn.nextInt()));
		int n = data.numInstances();
		int trainingEnd = (int) Math.floor(n * trainingDataSize);
		training = new Instances(data, 0, trainingEnd);
		testing = new Instances(data, trainingEnd-1, n-trainingEnd);
		if (isFirstColumnPredict){
			training.setClassIndex(0);
			testing.setClassIndex(0);
		} else {
			training.setClassIndex(training.numAttributes()-1);
			testing.setClassIndex(testing.numAttributes()-1);			
		}

	}

	public Instances getTraining() {
		return training;
	}

	public Instances getTest() {
		return testing;
	}

	
	public double getTrainingDataSize() {
		return trainingDataSize;
	}

	public void setTrainingDataSize(double trainingDataSize) {
		this.trainingDataSize = trainingDataSize;
	}

	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}

	
	public boolean isFirstColumnPredict() {
		return isFirstColumnPredict;
	}

	public void setFirstColumnPredict(boolean isFirstColumnPredict) {
		this.isFirstColumnPredict = isFirstColumnPredict;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
