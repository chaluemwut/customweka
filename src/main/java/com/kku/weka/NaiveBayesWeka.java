package com.kku.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class NaiveBayesWeka extends BaseComputation {
	private static String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	private static int repeatLoop = 500;
//	private static double[] trainingSizeLst = new double[] { 0.25, 0.50, 0.75 };

//	public static Instances loadData(String arffFileName) throws IOException {
//		String filePath = path_file + "data/arff/" + arffFileName;
//		BufferedReader reader = new BufferedReader(new FileReader(filePath));
//		ArffReader arff = new ArffReader(reader);
//		Instances data = arff.getData();
//		Random rn = new Random();
//		data.randomize(new Random(rn.nextInt()));
//		return data;
//	}
//
//	public static double mean(List<Double> p) {
//		double sum = 0;
//		for (Double d : p) {
//			sum += d.doubleValue();
//		}
//		return sum / p.size();
//	}

	public static void main(String[] args) {
//		DataSets[] dataSetList = new DataSets[1];
//		try {
//			DataSets letter = new DataSets();
//			letter.setData(loadData("letter.p2.arff"));
//			letter.setName("letter");
//			letter.setFirstColumnPredict(true);
//			dataSetList[0] = letter;

//			DataSets adult = new DataSets();
//			adult.setData(loadData("adult.arff"));
//			adult.setName("adult");
//			adult.setFirstColumnPredict(false);
//			dataSetList[0] = adult;
			
//
//			DataSets ad = new DataSets();
//			ad.setData(loadData("ad.arff"));
//			ad.setName("ad");
//			ad.setFirstColumnPredict(false);
//			dataSetList[2] = ad;
			
//			DataSets heartDis = new DataSets();
//			heartDis.setData(loadData("HeartDisease.arff"));
//			heartDis.setName("HeartDisease");
//			heartDis.setFirstColumnPredict(false);
//			dataSetList[0] = heartDis;
			
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		NaiveBayesWeka obj = new NaiveBayesWeka();
		List<DataSets> dataSetList = obj.getStatLog();
		
		Map<String, Map<Double, List<Double>>> result = new HashMap<>();
		Map<Double , List<Double>> lstSize = null;
		for (DataSets dataSet : dataSetList) {
			lstSize = new HashMap<>();
			for (int i = 0; i < trainingSizeLst.length; i++) {
				double trainingSize = Double.parseDouble(trainingSizeLst[i]);
				List<Double> lst = new ArrayList<>();
				for (int j = 0; j < repeatLoop; j++) {
					NaiveBayes naiveBayes = new NaiveBayes();
					try {
						dataSet.setTrainingDataSize(trainingSize);
						dataSet.loader();
						naiveBayes.buildClassifier(dataSet.getTraining());
						naiveBayes.setUseKernelEstimator(true);
						
						Evaluation eTest = new Evaluation(dataSet.getTraining());
						eTest.evaluateModel(naiveBayes, dataSet.getTest());
						double acc = eTest.correct() / dataSet.getTest().numInstances();
						lst.add(acc);
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				lstSize.put(trainingSize, lst);
			}
			result.put(dataSet.getName(), lstSize);
		}
		
		for(Entry<String, Map<Double, List<Double>>> resultData : result.entrySet()){
			System.out.printf("*************** %s **************\n", resultData.getKey());
			Map<Double, List<Double>> value = resultData.getValue();
			for (int i = 0; i < trainingSizeLst.length; i++) {
				List<Double> data = value.get(Double.parseDouble(trainingSizeLst[i]));
				System.out.printf("%.2f = %.4f\n", Double.parseDouble(trainingSizeLst[i]), obj.mean(data));
			}
		}
		
	}
}
