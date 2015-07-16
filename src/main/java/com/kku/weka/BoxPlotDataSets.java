package com.kku.weka;

import java.io.IOException;

import weka.core.Instances;

public class BoxPlotDataSets extends BaseComputation {

	public void printData(int datasetIdx, int attr, double size) {
		DataSets data = getBinaryDataSets().get(datasetIdx);
		try {
			System.out.println(data.getName()+" size "+data.getData().numInstances());			
			Instances[] allData = splitData(data, size);
			Instances train = allData[0];
			String str = "[";
			for (int i = 0; i < train.numInstances(); i++) {
				int classIndex = train.classIndex();
				double classValue = train.instance(i).value(classIndex);
//				if (classValue == 0) {
					double d = train.instance(i).value(attr);
					str += d + " ";
//				}
			}
			str += "]";
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BoxPlotDataSets box = new BoxPlotDataSets();
//		box.printData(1, 3, 0.25);
//		box.printData(1, 3, 0.50);
		box.printData(0, 0, 1.00);
	}

}
