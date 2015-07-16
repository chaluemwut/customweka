package com.kku.ranknb;

import java.io.IOException;
import java.util.List;

import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;

import com.kku.util.WekaUtil;
import com.kku.weka.BaseComputation;
import com.kku.weka.DataSets;

public class MainCompare extends BaseComputation {

	public void processCompare() throws IOException {
		List<DataSets> dataSet = getStatLog();
		weka.classifiers.bayes.NaiveBayes nbWeka = new weka.classifiers.bayes.NaiveBayes() {
			public String toString() {
				return "Naive bayes";
			};
		};

		NaiveBayesMultinomial multiNb = new NaiveBayesMultinomial() {
			public String toString() {
				return "Multinomial";
			};
		};

		NaiveBayes[] nbList = new NaiveBayes[] { new GeneralWrapWeka(nbWeka),
				new GeneralWrapWeka(multiNb) };

		for (int i = 0; i < dataSet.size(); i++) {
			DataSets data = dataSet.get(i);
			System.out.println("Data set name : " + data.getName());
			Instances[] d = splitData(data, 0.75);
			Instances train = d[0];
			Instances test = d[1];
			double[] yTrue = WekaUtil.getY(test);
			for (int j = 0; j < nbList.length; j++) {
				NaiveBayes nb = nbList[j];
				nb.train(train);
				double[] result = nb.predict(test);
				int correct = computeCorrect(yTrue, result);
				double acc = (double) correct / (double) test.numInstances();
				System.out.println(String.format("nb %s acc %.4f", nb, acc));
			}
		}
	}

	public static void main(String[] args) {
		MainCompare main = new MainCompare();
		try {
			main.processCompare();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
