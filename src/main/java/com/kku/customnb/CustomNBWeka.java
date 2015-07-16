package com.kku.customnb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class CustomNBWeka {

	private String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	List<DescriptiveStatistics> oneStat;
	List<DescriptiveStatistics> zeroStat;
	List<Instance> oneClass;
	List<Instance> zeroClass;
	int numAttr;
	
	public Instances loadData(String arffFileName) {
		try {
			String filePath = path_file + "data/arff/test/" + arffFileName;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void buildClassifier(Instances trainingData) {
		int numData = trainingData.numInstances();
		numAttr = trainingData.numAttributes();

		oneClass = new ArrayList<>();
		zeroClass = new ArrayList<>();
		for (int i = 0; i < numData; i++) {
			Instance instance = trainingData.instance(i);
			double classPredict = instance.value(0);
			if (classPredict == 0) {
				zeroClass.add(instance);
			} else {
				oneClass.add(instance);
			}
		}

		double[][] oneIns = converToArray(numAttr, oneClass);
		double[][] zeroIns = converToArray(numAttr, zeroClass);

		oneStat = convertToStat(numAttr, oneIns);
		zeroStat = convertToStat(numAttr, zeroIns);
		
		for (int i = 1; i < numAttr; i++) {
			DescriptiveStatistics oneDesc = oneStat.get(i);
			DescriptiveStatistics zeroDesc = zeroStat.get(i);
//			NormalDistribution zeroND = new NormalDistribution(zeroDesc.getMean(), zeroDesc.getStandardDeviation());
//			NormalDistribution oneND = new NormalDistribution(oneDesc.getMean(), oneDesc.getStandardDeviation());
			String str1 = String.format("mean %.3f %.3f\n", zeroDesc.getMean(), oneDesc.getMean());
			String str2 = String.format("sd %.3f %.3f\n", zeroDesc.getStandardDeviation(), oneDesc.getStandardDeviation());
			System.out.println(str1);
			System.out.println(str2);
		}
	}

	private List<DescriptiveStatistics> convertToStat(int numAttr,
			double[][] posIns) {
		List<DescriptiveStatistics> retStat = new ArrayList<>();
		for (int i = 0; i < numAttr; i++) {
			DescriptiveStatistics desc = new DescriptiveStatistics(posIns[i]);
			retStat.add(desc);
		}
		return retStat;
	}

	private double[][] converToArray(int numAttr, List<Instance> positive) {
		double[][] ret = new double[numAttr][positive.size()];
		for (int i = 0; i < numAttr; i++) {
			for (int j = 0; j < positive.size(); j++) {
				Instance ins = positive.get(j);
				ret[i][j] = ins.value(i);
			}
		}
		return ret;
	}

	public double prediction(Instance testData) {
		List<Double> zeroProb = new ArrayList<>();
		List<Double> oneProb = new ArrayList<>();
		int n = oneClass.size()+zeroClass.size();
		for (int i = 1; i < numAttr; i++) {
			DescriptiveStatistics oneDesc = oneStat.get(i);
			DescriptiveStatistics zeroDesc = zeroStat.get(i);
			NormalDistribution zeroND = new NormalDistribution(zeroDesc.getMean(), zeroDesc.getStandardDeviation());
			NormalDistribution oneND = new NormalDistribution(oneDesc.getMean(), oneDesc.getStandardDeviation());
			double x = testData.value(i);
			zeroProb.add(zeroND.cumulativeProbability(x));
			oneProb.add(oneND.cumulativeProbability(x));
		}
		double oneClassProb = oneClass.size()/(double)n;
		double zeroClassProb = zeroClass.size()/(double)n;
		double oneAllProb = oneClassProb*(oneProb.stream().reduce(1.0, (a, b) -> a * b));
		double zeroAllProb = zeroClassProb*(zeroProb.stream().reduce(1.0, (a, b) -> a * b));
		System.out.println(oneAllProb);
		System.out.println(zeroAllProb);
		return 0;
	}

	public static void main(String[] args) {
		
		NormalDistribution nd = new NormalDistribution();
		double d = nd.probability(-3, 3);
		System.out.println(d);
		
//		CustomNB nb = new CustomNB();
//		Instances instances = nb.loadData("letter.p2.arff");
//		Instance testIns = nb.loadData("letter.p2.test.arff").instance(0);		
//		nb.buildClassifier(instances);
//		nb.prediction(testIns);
//		List<Double> lst = Arrays.asList(new Double[]{2.0, 2.0, 2.0});
//		double p = lst.stream().reduce(1.0, (a,b)->a*b);
//		System.out.println(p);
	}

}
