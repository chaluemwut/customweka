package com.kku.ranknb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.kku.customnb.ProbBean;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class RankNaiveBayes extends AbstractNaiveBayes {
	List<Instance> oneClass;
	List<Instance> zeroClass;

	@Override
	public void train(Instances train) {
		this.classIndex = train.classIndex();
		this.train = train;
		int numData = train.numInstances();
		numAttr = train.numAttributes();

		if (classIndex == 0) {
			startAttr = 1;
			endAttr = numAttr;
		} else {
			startAttr = 0;
			endAttr = numAttr - 1;
		}

		oneClass = new ArrayList<>();
		zeroClass = new ArrayList<>();
		for (int i = 0; i < numData; i++) {
			Instance instance = train.instance(i);
			double classPredict = instance.value(classIndex);
			if (classPredict == 0) {
				zeroClass.add(instance);
			} else {
				oneClass.add(instance);
			}
		}

		numZeroClass = zeroClass.size();
		numOneClass = oneClass.size();

		double[][] oneIns = converToArray(numAttr, oneClass);
		double[][] zeroIns = converToArray(numAttr, zeroClass);

		oneStat = convertToStat(numAttr, oneIns);
		zeroStat = convertToStat(numAttr, zeroIns);
	}

	@Override
	public double predict(Instance test) {
		predictionReport = new ArrayList<>();
		List<Double> zeroProb = new ArrayList<>();
		List<Double> oneProb = new ArrayList<>();
		for (int i = startAttr; i < endAttr; i++) {
			Attribute attr = test.attribute(i);
			double[] zeroProbReport = new double[3];
			double[] oneProbReport = new double[3];
			DescriptiveStatistics oneDesc = oneStat.get(i);
			DescriptiveStatistics zeroDesc = zeroStat.get(i);
			NormalDistribution zeroND = new NormalDistribution(
					zeroDesc.getMean(), zeroDesc.getStandardDeviation());
			NormalDistribution oneND = new NormalDistribution(
					oneDesc.getMean(), oneDesc.getStandardDeviation());
			double x = test.value(i);
			double probZero = zeroND.density(x);
			double probOne = oneND.density(x);
			zeroProb.add(probZero);
			oneProb.add(probOne);

			zeroProbReport[0] = zeroDesc.getMean();
			zeroProbReport[1] = zeroDesc.getStandardDeviation();
			zeroProbReport[2] = probZero;

			oneProbReport[0] = oneDesc.getMean();
			oneProbReport[1] = oneDesc.getStandardDeviation();
			oneProbReport[2] = probOne;
			predictionReport.add(new ProbBean(attr.name(), zeroProbReport,
					oneProbReport, x));
		}
		int n = oneClass.size() + zeroClass.size();
		oneClassProb = oneClass.size() / (double) n;
		zeroClassProb = zeroClass.size() / (double) n;

		oneAllProb = oneClassProb
				* (oneProb.stream().reduce(1.0, (a, b) -> a * b));
		zeroAllProb = zeroClassProb
				* (zeroProb.stream().reduce(1.0, (a, b) -> a * b));

		if (oneAllProb > zeroAllProb) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "RankNaiveBayes";
	}
}
