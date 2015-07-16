package com.kku.customnb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import weka.core.Instance;
import weka.core.Instances;

public class WekaNaiveBayes extends AbstractNaiveBayes {
	
	List<DescriptiveStatistics> oneStat;
	List<DescriptiveStatistics> zeroStat;
	List<Instance> oneClass;
	List<Instance> zeroClass;
	int numAttr;

	@Override
	public void train(Instances train) {
		int numData = train.numInstances();
		numAttr = train.numAttributes();

		oneClass = new ArrayList<>();
		zeroClass = new ArrayList<>();
		for (int i = 0; i < numData; i++) {
			Instance instance = train.instance(i);
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
			String str1 = String.format("mean %.3f %.3f\n", zeroDesc.getMean(),
					oneDesc.getMean());
			String str2 = String.format("sd %.3f %.3f\n",
					zeroDesc.getStandardDeviation(),
					oneDesc.getStandardDeviation());
			System.out.println(str1);
			System.out.println(str2);
		}
	}

	@Override
	public double predict(Instance test) {
		return 0;
	}

	@Override
	public double[] predict(Instances test) {

		return null;
	}

}
