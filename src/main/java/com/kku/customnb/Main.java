package com.kku.customnb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import com.kku.util.TableBuilder;
import com.kku.weka.BaseComputation;
import com.kku.weka.DataSets;

public class Main extends BaseComputation {
	private static int loop = 10;

	public String str4digit(double[] d) {
		String str = "[";
		for (int i = 0; i < d.length; i++) {
			str+=String.format("%.4f ,", d[i]);
		}
		return str.substring(0, str.length()-1)+"]";
	}
	
	public void process() {
		List<DataSets> dataSets =getBinaryDataSets();
		for (DataSets data : dataSets) {
			System.out.println("**************** " + data.getName());
			String[] trainSizeData = getTrainSize();
			List<String> allAttr = getAttribute(data.getData());
			int attrSize = allAttr.size();
			double[][] zeroMean25 = new double[loop][attrSize];
			double[][] zeroSD25 = new double[loop][attrSize];
			double[][] oneMean25 = new double[loop][attrSize];
			double[][] oneSD25 = new double[loop][attrSize];

			double[][] zeroMean50 = new double[loop][attrSize];
			double[][] zeroSD50 = new double[loop][attrSize];
			double[][] oneMean50 = new double[loop][attrSize];
			double[][] oneSD50 = new double[loop][attrSize];

			double[][] zeroMean75 = new double[loop][attrSize];
			double[][] zeroSD75 = new double[loop][attrSize];
			double[][] oneMean75 = new double[loop][attrSize];
			double[][] oneSD75 = new double[loop][attrSize];
			
			for (int i = 0; i < trainSizeData.length; i++) {
				String trainSize = trainSizeData[i];

				for (int j = 0; j < loop; j++) {
					AnalysisNaiveBayes anaNb = new AnalysisNaiveBayes();
					try {
						List<MeanSDBean> report = anaNb.analysisLoop(trainSize,
								data);
						for (int k = 0; k < report.size(); k++) {
							MeanSDBean meanSDBean = report.get(k);
							double[] zeroClass = meanSDBean.getZeroClass();
							double[] oneClass = meanSDBean.getOneClass();
							if ("0.25".equals(trainSize)) {
								zeroMean25[j][k] = zeroClass[0];
								zeroSD25[j][k] = zeroClass[1];
								oneMean25[j][k] = oneClass[0];
								oneSD25[j][k] = oneClass[1];
							} else if ("0.50".equals(trainSize)) {
								zeroMean50[j][k] = zeroClass[0];
								zeroSD50[j][k] = zeroClass[1];
								oneMean50[j][k] = oneClass[0];
								oneSD50[j][k] = oneClass[1];
							} else {
								zeroMean75[j][k] = zeroClass[0];
								zeroSD75[j][k] = zeroClass[1];
								oneMean75[j][k] = oneClass[0];
								oneSD75[j][k] = oneClass[1];
							}
						}
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
				}
			}
			TableBuilder tlb = new TableBuilder();
			RealMatrix zeroMean25Matrix = new Array2DRowRealMatrix(zeroMean25);
			RealMatrix zeroMean50Matrix = new Array2DRowRealMatrix(zeroMean50);
			RealMatrix zeroMean75Matrix = new Array2DRowRealMatrix(zeroMean75);
			System.out.println("Negative class - mean");
			for (int m = 0; m < allAttr.size(); m++) {
				String attrName = allAttr.get(m);
				tlb.addRow(attrName+" - 25%", str4digit(zeroMean25Matrix.getColumn(m)));
				tlb.addRow(attrName+" - 50%", str4digit(zeroMean50Matrix.getColumn(m)));
				tlb.addRow(attrName+" - 75%", str4digit(zeroMean75Matrix.getColumn(m)));
			}
			System.out.println(tlb);

			TableBuilder tlbSD = new TableBuilder();
			RealMatrix zeroSD25Matrix = new Array2DRowRealMatrix(zeroSD25);
			RealMatrix zeroSD50Matrix = new Array2DRowRealMatrix(zeroSD50);
			RealMatrix zeroSD75Matrix = new Array2DRowRealMatrix(zeroSD75);
			System.out.println("Negative class - SD");
			for (int m = 0; m < allAttr.size(); m++) {
				String attrName = allAttr.get(m);
				tlbSD.addRow(attrName+" - 25%", str4digit(zeroSD25Matrix.getColumn(m)));
				tlbSD.addRow(attrName+" - 50%", str4digit(zeroSD50Matrix.getColumn(m)));
				tlbSD.addRow(attrName+" - 75%", str4digit(zeroSD75Matrix.getColumn(m)));
			}
			System.out.println(tlbSD);
			
			TableBuilder tlbOneMean = new TableBuilder();
			RealMatrix oneMean25Matrix = new Array2DRowRealMatrix(oneMean25);
			RealMatrix oneMean50Matrix = new Array2DRowRealMatrix(oneMean50);
			RealMatrix oneMean75Matrix = new Array2DRowRealMatrix(oneMean75);
			System.out.println("Positive class - mean");
			for (int m = 0; m < allAttr.size(); m++) {
				String attrName = allAttr.get(m);
				tlbOneMean.addRow(attrName+" - 25%", str4digit(oneMean25Matrix.getColumn(m)));
				tlbOneMean.addRow(attrName+" - 50%", str4digit(oneMean50Matrix.getColumn(m)));
				tlbOneMean.addRow(attrName+" - 75%", str4digit(oneMean75Matrix.getColumn(m)));
			}
			System.out.println(tlbOneMean);	
			
			TableBuilder tlbOneSD = new TableBuilder();
			RealMatrix oneSD25Matrix = new Array2DRowRealMatrix(oneSD25);
			RealMatrix oneSD50Matrix = new Array2DRowRealMatrix(oneSD50);
			RealMatrix oneSD75Matrix = new Array2DRowRealMatrix(oneSD75);
			System.out.println("Positive class - SD");
			for (int m = 0; m < allAttr.size(); m++) {
				String attrName = allAttr.get(m);
				tlbOneSD.addRow(attrName+" - 25%", str4digit(oneSD25Matrix.getColumn(m)));
				tlbOneSD.addRow(attrName+" - 50%", str4digit(oneSD50Matrix.getColumn(m)));
				tlbOneSD.addRow(attrName+" - 75%", str4digit(oneSD75Matrix.getColumn(m)));
			}
			System.out.println(tlbOneSD);				
			
		}
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.process();
	}
}
