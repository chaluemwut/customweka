package com.kku.weka;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.estimators.Estimator;
import weka.estimators.NormalEstimator;

public class ComputeMean extends BaseComputation {

	public ComputeMean() {
	}

	public void parameterReport(Instances trainingData, Instances testData) {

	}

	public Double[] normalEst(NormalEstimator estimator)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Double[] ret = new Double[2];
		Class<? extends NormalEstimator> cls = estimator.getClass();

		Field mFieldMean = cls.getDeclaredField("m_Mean");
		mFieldMean.setAccessible(true);
		Double m_Mean = (Double) mFieldMean.get(estimator);
		ret[0] = m_Mean;

		Field mFieldSD = cls.getDeclaredField("m_StandardDev");
		mFieldSD.setAccessible(true);
		Double m_StandardDev = (Double) mFieldSD.get(estimator);
		ret[1] = m_StandardDev;
		return ret;
	}

	public Double[] wekaDistribution(NaiveBayes naiveBayes) {
		List<Double> retList = new ArrayList<>();
		Class<? extends NaiveBayes> cls = naiveBayes.getClass();
		try {
			Field fiedDis = cls.getDeclaredField("m_Distributions");
			fiedDis.setAccessible(true);
			Estimator[][] m_Dis = (Estimator[][]) fiedDis.get(naiveBayes);
			Double[][] data = new Double[m_Dis.length][4];
			for (int i = 0; i < m_Dis.length; i++) {
				Double[] ret = new Double[4];
				Estimator[] estimators = m_Dis[i];
				NormalEstimator negative = (NormalEstimator) estimators[0];
				Double[] negativeRet = normalEst(negative);
				retList.add(negativeRet[0]);
				retList.add(negativeRet[1]);
				NormalEstimator positive = (NormalEstimator) estimators[1];
				Double[] positiveRet = normalEst(positive);
				retList.add(positiveRet[0]);
				retList.add(positiveRet[1]);
			}
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return retList.toArray(new Double[retList.size()]);
	}

	public void process() throws Exception {
		DataSets[] dataSetList = getDataSets();
		Map<String, Map<String, List<Double[]>>> result = new HashMap<>();
		for (DataSets dataSets : dataSetList) {
			System.out.println("data set " + dataSets.getName());
			Map<String, List<Double[]>> sizeMap = new HashMap<>();
			for (int i = 0; i < trainingSizeLst.length; i++) {
				String trainingDataSize = trainingSizeLst[i];
				List<Double[]> dataLoop = new ArrayList<>();
				for (int j = 0; j < repeatLoop; j++) {
					Instances[] allData = splitData(dataSets,
							Double.parseDouble(trainingDataSize));
					Instances trainData = allData[0];
					Instances testData = allData[1];
					NaiveBayes naiveBayes = new NaiveBayes();
					naiveBayes.buildClassifier(trainData);

					Double[] distMean = wekaDistribution(naiveBayes);
					List<Double> lst = new ArrayList<Double>(
							Arrays.asList(distMean));

					Evaluation eTest = new Evaluation(trainData);
					eTest.evaluateModel(naiveBayes, testData);
					double acc = eTest.correct() / testData.numInstances();
					lst.add(acc);
					dataLoop.add(lst.toArray(new Double[lst.size()]));
				}
				sizeMap.put(trainingDataSize, dataLoop);
			}
			result.put(dataSets.getName(), sizeMap);
		}
		System.out.println(result);
	}
	
	public void processTest() throws Exception{
		Instances train = loadData("test/iris.arff");
		train.setClassIndex(train.numAttributes()-1);
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(train);
		
//		Evaluation eTest = new Evaluation(train);
		
		Instances test = loadData("test/iris.test.arff");
		test.setClassIndex(test.numAttributes()-1);
		double[] d = nb.distributionForInstance(test.instance(0));
		System.out.println(Arrays.toString(d));
		
//		
//		eTest.evaluateModel(nb, test);
		
//		NaiveBayes nb = new NaiveBayes();
//		nb.buildClassifier(train);
//		Evaluation eTest = new Evaluation(train);
//		eTest.evaluateModel(nb, test);
//		System.out.println(eTest);
	}

	public Map<String, Map<String, List<Double[]>>> getResult() {
		ObjectInputStream objectinputstream = null;
		try {
			FileInputStream streamIn = new FileInputStream(
					"/home/off/kku/nb/result/result.dat");
			objectinputstream = new ObjectInputStream(streamIn);
			Map<String, Map<String, List<Double[]>>> readCase = (Map<String, Map<String, List<Double[]>>>) objectinputstream
					.readObject();
			return readCase;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (objectinputstream != null) {
				try {
					objectinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void save(Map<String, Map<String, List<Double[]>>> result) {
		try {
			FileOutputStream fout = new FileOutputStream(
					"/home/off/kku/nb/result/result.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(result);
			oos.close();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void plot(String dataSetName, int feature) {
		Map<String, Map<String, List<Double[]>>> result = getResult();
		Map<String, List<Double[]>> allData = result.get(dataSetName);
		List<Double> lst25 = new ArrayList<>();
		List<Double> lst50 = new ArrayList<>();
		List<Double> lst75 = new ArrayList<>();
		for (int i = 0; i < trainingSizeLst.length; i++) {
			String trainingDataSize = trainingSizeLst[i];
			List<Double[]> lstData = allData.get(trainingDataSize);
			for (int j = 0; j < lstData.size(); j++) {
				Double[] data = lstData.get(j);
				// int feature = 0;
				int idx = feature * 4;
				Double meanPos = data[idx];
				Double sdPos = data[++idx];
				Double meanNg = data[++idx];
				Double sdNg = data[++idx];
				if ("0.25".equals(trainingDataSize)) {
					lst25.add(sdNg);
				} else if ("0.50".equals(trainingDataSize)) {
					lst50.add(sdNg);
				} else {
					lst75.add(sdNg);
				}
			}
		}

//		 PlotMean plotMean = new PlotMean(lst25, lst50, lst75);
		// System.out.println(lst25);
		// System.out.println(lst50);
		// System.out.println(lst75);
		System.out.printf("%.4f,%.4f,%.4f\n", mean(lst25), mean(lst50),
				mean(lst75));
//		 plotMean.plot();
	}

	public static void main(String[] args) {
		ComputeMean obj = new ComputeMean();
		try {
			obj.processTest();
//			for (int i = 0; i < 14; i++) {
//				obj.plot("adult", i);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
