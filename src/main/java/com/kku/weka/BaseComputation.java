package com.kku.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class BaseComputation {
	private String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";
	int repeatLoop = 2;
	static String[] trainingSizeLst = new String[] { "0.25", "0.50", "0.75" };

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

	public String[] getTrainSize() {
		return trainingSizeLst;
	}

	public List<String> getAttribute(Instances data) {
		List<String> ret = new ArrayList<>();
		Instance firstIns = data.firstInstance();
		for (int i = 0; i < firstIns.numAttributes(); i++) {
			Attribute attr = firstIns.attribute(i);
			ret.add(attr.name());
		}
		return ret;
	}

	public DataSets[] getDataSets() {
		DataSets[] dataSets = new DataSets[3];

		DataSets letter = new DataSets();
		letter.setData(loadData("letter.p2.arff"));
		letter.setName("letter");
		letter.setFirstColumnPredict(true);
		dataSets[0] = letter;

		DataSets adult = new DataSets();
		adult.setData(loadData("adult.arff"));
		adult.setName("adult");
		adult.setFirstColumnPredict(false);
		dataSets[1] = adult;

		// DataSets ad = new DataSets();
		// ad.setData(loadData("ad.arff"));
		// ad.setName("ad");
		// ad.setFirstColumnPredict(false);
		// dataSets[2] = ad;

		DataSets heartDis = new DataSets();
		heartDis.setData(loadData("HeartDisease.arff"));
		heartDis.setName("HeartDisease");
		heartDis.setFirstColumnPredict(false);
		dataSets[2] = heartDis;

		return dataSets;
	}

	public List<DataSets> getStatLog() {
		List<DataSets> ret = new ArrayList<>();

		DataSets australian = new DataSets();
		australian.setData(loadData("statlog/australian.arff"));
		australian.setName("australian");
		australian.setFirstColumnPredict(false);
		ret.add(australian);

		DataSets german = new DataSets();
		german.setData(loadData("statlog/german.arff"));
		german.setName("german");
		german.setFirstColumnPredict(false);
		ret.add(german);

		DataSets sat = new DataSets();
		sat.setData(loadData("statlog/sat.arff"));
		sat.setName("sat");
		sat.setFirstColumnPredict(false);
		ret.add(sat);

//		DataSets segment = new DataSets();
//		segment.setData(loadData("statlog/segment.arff"));
//		segment.setName("segment");
//		segment.setFirstColumnPredict(false);
//		ret.add(segment);
		
		DataSets shuttle = new DataSets();
		shuttle.setData(loadData("statlog/shuttle.arff"));
		shuttle.setName("shuttle");
		shuttle.setFirstColumnPredict(false);
		ret.add(shuttle);
	
		DataSets vehicle = new DataSets();
		vehicle.setData(loadData("statlog/vehicle.arff"));
		vehicle.setName("vehicle");
		vehicle.setFirstColumnPredict(false);
		ret.add(vehicle);		
		
		return ret;
	}
	
	public List<DataSets> getBinaryDataSets() {
		List<DataSets> ret = new ArrayList<>();

		DataSets heartDis = new DataSets();
		heartDis.setData(loadData("binary/HeartDisease.arff"));
		heartDis.setName("HeartDisease");
		heartDis.setFirstColumnPredict(false);
		ret.add(heartDis);

		DataSets letter = new DataSets();
		letter.setData(loadData("binary/letter.p2.arff"));
		letter.setName("letter");
		letter.setFirstColumnPredict(true);
		ret.add(letter);

		DataSets adult = new DataSets();
		adult.setData(loadData("binary/adult.arff"));
		adult.setName("adult");
		adult.setFirstColumnPredict(false);
		ret.add(adult);

		return ret;
	}

	public double mean(List<Double> p) {
		double sum = 0;
		for (Double d : p) {
			sum += d.doubleValue();
		}
		return sum / p.size();
	}

	public Instances[] splitData(Instances data, double trainingDataSize)
			throws IOException {
		Random rn = new Random();
		data.randomize(new Random(rn.nextInt()));
		int n = data.numInstances();
		int trainingEnd = (int) Math.floor(n * trainingDataSize);
		Instances training = new Instances(data, 0, trainingEnd);
		training.setClassIndex(training.numAttributes() - 1);
		Instances testing = new Instances(data, trainingEnd - 1, n
				- trainingEnd);
		testing.setClassIndex(testing.numAttributes() - 1);
		return new Instances[] { training, testing };
	}

	public Instances[] splitData(DataSets dataSets, double trainingDataSize)
			throws IOException {
		Instances data = dataSets.getData();
		Random rn = new Random();
		data.randomize(new Random(rn.nextInt()));
		int n = data.numInstances();
		int trainingEnd = (int) Math.floor(n * trainingDataSize);
		Instances training = new Instances(data, 0, trainingEnd);
		Instances testing = new Instances(data, trainingEnd - 1, n
				- trainingEnd);
		if (dataSets.isFirstColumnPredict()) {
			training.setClassIndex(0);
			testing.setClassIndex(0);
		} else {
			training.setClassIndex(training.numAttributes() - 1);
			testing.setClassIndex(testing.numAttributes() - 1);
		}
		return new Instances[] { training, testing };
	}
	
	public int computeCorrect(double[] a, double[] b) {
		int d = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == b[i])
				d++;
		}
		return d;
	}

}
