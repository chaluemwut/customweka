package com.kku.customnb;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.core.Instance;
import weka.core.Instances;

import com.kku.customnb.bean.ResultBean;
import com.kku.util.ReportUtil;
import com.kku.weka.BaseComputation;
import com.kku.weka.DataSets;

public class AnalysisNaiveBayes extends BaseComputation {

	public List<MeanSDBean> analysisLoop(String trainSize, DataSets dataSet)
			throws NumberFormatException, IOException {
		Instances allData = dataSet.getData();
		Instance testData = dataSet.getData().instance(
				allData.numInstances() - 1);
		Instances[] splitData = splitData(dataSet,
				Double.parseDouble(trainSize));
		Instances train = splitData[0];
		PDFNaiveBayes nb;
		if (dataSet.isFirstColumnPredict()) {
			nb = new PDFNaiveBayes(0);
		} else {
			nb = new PDFNaiveBayes(train.numAttributes() - 1);
		}
		nb.train(train);
		nb.predict(testData);
		return nb.getTrainingReport();
	}

	@Deprecated
	public Map<String, ResultBean> analysis() {
		Map<String, ResultBean> mResult = new HashMap<>();
		List<DataSets> dataSet = getBinaryDataSets();
		List<MeanSDBean> lstMean25 = null;
		double zero25AllProb = 0;
		double one25AllProb = 0;

		List<MeanSDBean> lstMean50 = null;
		List<ProbBean> prob25 = null;
		double zero50AllProb = 0;
		double one50AllProb = 0;

		List<MeanSDBean> lstMean75 = null;
		List<ProbBean> prob50 = null;
		double zero75AllProb = 0;
		double one75AllProb = 0;

		List<ProbBean> prob75 = null;
		int[][] numClass = new int[3][];
		for (int i = 0; i < dataSet.size(); i++) {
			DataSets data = dataSet.get(i);
			Instances allData = data.getData();
			Instance testData = data.getData().instance(
					allData.numInstances() - 1);

			String[] trainSize = getTrainSize();
			for (int j = 0; j < trainSize.length; j++) {
				String size = trainSize[j];
				try {
					Instances[] splitData = splitData(data,
							Double.parseDouble(size));
					Instances train = splitData[0];
					PDFNaiveBayes nb;
					if (data.isFirstColumnPredict()) {
						nb = new PDFNaiveBayes(0);
					} else {
						nb = new PDFNaiveBayes(train.numAttributes() - 1);
					}
					nb.train(train);
					nb.predict(testData);
					if ("0.25".equals(size)) {
						lstMean25 = nb.getTrainingReport();
						numClass[0] = nb.getNumClass();
						prob25 = nb.getPredictReport();
						zero25AllProb = nb.getZeroAllProb();
						one25AllProb = nb.getOneAllProb();

					} else if ("0.50".equals(size)) {
						lstMean50 = nb.getTrainingReport();
						numClass[1] = nb.getNumClass();
						prob50 = nb.getPredictReport();
						zero50AllProb = nb.getZeroAllProb();
						one50AllProb = nb.getOneAllProb();

					} else {
						lstMean75 = nb.getTrainingReport();
						numClass[2] = nb.getNumClass();
						prob75 = nb.getPredictReport();
						zero75AllProb = nb.getZeroAllProb();
						one75AllProb = nb.getOneAllProb();

					}
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(String.format(
					"************** %s ****************", data.getName()));
			ReportUtil.print(lstMean25, lstMean50, lstMean75, numClass);
			double[] allProb = new double[] { zero25AllProb, zero50AllProb,
					zero75AllProb, one25AllProb, one50AllProb, one75AllProb };
			ReportUtil.printTestInstance(testData);
			System.out.println();
			ReportUtil.printProb(prob25, prob50, prob75, allProb);
			mResult.put(data.getName(), null);
		}

		return mResult;
	}

	public static void main(String[] args) {
		AnalysisNaiveBayes m = new AnalysisNaiveBayes();
		List<DataSets> dataSet = m.getBinaryDataSets();
		List<MeanSDBean> lstMean25 = null;
		double zero25AllProb = 0;
		double one25AllProb = 0;

		List<MeanSDBean> lstMean50 = null;
		List<ProbBean> prob25 = null;
		double zero50AllProb = 0;
		double one50AllProb = 0;

		List<MeanSDBean> lstMean75 = null;
		List<ProbBean> prob50 = null;
		double zero75AllProb = 0;
		double one75AllProb = 0;

		List<ProbBean> prob75 = null;
		int[][] numClass = new int[3][];
		for (int i = 0; i < dataSet.size(); i++) {
			DataSets data = dataSet.get(i);
			Instances allData = data.getData();
			Instance testData = data.getData().instance(
					allData.numInstances() - 1);

			String[] trainSize = m.getTrainSize();
			for (int j = 0; j < trainSize.length; j++) {
				String size = trainSize[j];
				try {
					Instances[] splitData = m.splitData(data,
							Double.parseDouble(size));
					Instances train = splitData[0];
					PDFNaiveBayes nb;
					if (data.isFirstColumnPredict()) {
						nb = new PDFNaiveBayes(0);
					} else {
						nb = new PDFNaiveBayes(train.numAttributes() - 1);
					}
					nb.train(train);
					nb.predict(testData);
					if ("0.25".equals(size)) {
						lstMean25 = nb.getTrainingReport();
						numClass[0] = nb.getNumClass();
						prob25 = nb.getPredictReport();
						zero25AllProb = nb.getZeroAllProb();
						one25AllProb = nb.getOneAllProb();

					} else if ("0.50".equals(size)) {
						lstMean50 = nb.getTrainingReport();
						numClass[1] = nb.getNumClass();
						prob50 = nb.getPredictReport();
						zero50AllProb = nb.getZeroAllProb();
						one50AllProb = nb.getOneAllProb();

					} else {
						lstMean75 = nb.getTrainingReport();
						numClass[2] = nb.getNumClass();
						prob75 = nb.getPredictReport();
						zero75AllProb = nb.getZeroAllProb();
						one75AllProb = nb.getOneAllProb();

					}
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(String.format(
					"************** %s ****************", data.getName()));
			ReportUtil.print(lstMean25, lstMean50, lstMean75, numClass);
			double[] allProb = new double[] { zero25AllProb, zero50AllProb,
					zero75AllProb, one25AllProb, one50AllProb, one75AllProb };
			ReportUtil.printTestInstance(testData);
			System.out.println();
			ReportUtil.printProb(prob25, prob50, prob75, allProb);
		}
	}

}
