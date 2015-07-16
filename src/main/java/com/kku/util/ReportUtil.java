package com.kku.util;

import java.util.List;

import weka.core.Attribute;
import weka.core.Instance;

import com.kku.customnb.MeanSDBean;
import com.kku.customnb.ProbBean;

public class ReportUtil {

	private static String str4digit(double d) {
		return String.format("%.4f", d);
	}

	private static String str2digit(double d) {
		return String.format("%.2f", d);
	}

	public static void print(List<MeanSDBean> lstMean25,
			List<MeanSDBean> lstMean50, List<MeanSDBean> lstMean75,
			int[][] numClass) {
		ReportUtil.print(lstMean25, lstMean50, lstMean75, true);
		ReportUtil.print(lstMean25, lstMean50, lstMean75, false);
		printNumClass(numClass);
	}

	public static void printProb(List<ProbBean> prob25, List<ProbBean> prob50,
			List<ProbBean> prob75, double[] probPredict) {
		System.out.println("Negative class");
		printProb(prob25, prob50, prob75, probPredict, true);
		System.out.println("Positive class");
		printProb(prob25, prob50, prob75, probPredict, false);
	}

	public static void printTestInstance(Instance data) {
		String str = "x [";
		for (int i = 0; i < data.numAttributes(); i++) {
			Attribute attr = data.attribute(i);
			str += String.format("%s=%.2f, ", attr.name(), data.value(attr));
		}
		System.out.println(str += "]");
	}

	private static void printProb(List<ProbBean> prob25, List<ProbBean> prob50,
			List<ProbBean> prob75, double[] probPredict, boolean isZeroClass) {
		TableBuilder tlb = new TableBuilder();
		tlb.addRow("Size", "25%", "50%", "75%");
		for (int i = 0; i < prob25.size(); i++) {
			ProbBean bean25 = prob25.get(i);
			ProbBean bean50 = prob50.get(i);
			ProbBean bean75 = prob75.get(i);
			double[] cls25;
			double[] cls50;
			double[] cls75;
			if (isZeroClass) {
				cls25 = bean25.getZeroProb();
				cls50 = bean50.getZeroProb();
				cls75 = bean75.getZeroProb();
			} else {
				cls25 = bean25.getOneProb();
				cls50 = bean50.getOneProb();
				cls75 = bean75.getOneProb();
			}
			String str25 = String.format("P(x)[m=%.4f, sd=%.4f]=%.4f",
					cls25[0], cls25[1], cls25[2]);
			String str50 = String.format("P(x)[m=%.4f, sd=%.4f]=%.4f",
					cls50[0], cls50[1], cls50[2]);
			String str75 = String.format("P(x)[m=%.4f, sd=%.4f]=%.4f",
					cls75[0], cls75[1], cls75[2]);
			tlb.addRow(
					String.format("%s=%.4f", bean25.getAttrName(),
							bean25.getDataValue()), str25, str50, str75);
//			 tlb.addRow(
//			 String.format("%s=%.4f", bean25.getAttrName(),
//			 bean25.getDataValue()), str4digit(cls25[2]),
//			 str4digit(cls50[2]), str4digit(cls75[2]));
		}
		if (isZeroClass) {
			tlb.addRow("All prob ", "" + probPredict[0], "" + probPredict[1],
					"" + probPredict[2]);
		} else {
			tlb.addRow("All prob ", "" + probPredict[3], "" + probPredict[4],
					"" + probPredict[5]);
		}
		System.out.println(tlb);
	}

	private static void printNumClass(int[][] numClass) {
		int[] numClass25 = numClass[0];
		int[] numClass50 = numClass[1];
		int[] numClass75 = numClass[2];
		System.out.println("**********Report training data**************");
		TableBuilder tlb = new TableBuilder();
		tlb.addRow("Number of class", "25%", "50%", "75%");
		tlb.addRow("Negative", "" + numClass25[0], "" + numClass50[0], ""
				+ numClass75[0]);
		tlb.addRow("Positive", "" + numClass25[1], "" + numClass50[1], ""
				+ numClass75[1]);
		tlb.addRow("Total ", "" + (numClass25[0] + numClass25[1]), ""
				+ (numClass50[0] + numClass50[1]), ""
				+ (numClass75[0] + numClass75[1]));
		System.out.println(tlb);
	}

	private static void print(List<MeanSDBean> lstMean25,
			List<MeanSDBean> lstMean50, List<MeanSDBean> lstMean75,
			boolean isZeroClass) {
		TableBuilder tlb = new TableBuilder();
		if (isZeroClass) {
			tlb.addRow("Negative class");
		} else {
			tlb.addRow("Positive class");
		}
		tlb.addRow("---", "----", "----", "----", "---", "----", "----");
		tlb.addRow("   ", "   ", " Mean ", "  |  ", "   ", " SD ", "   ");
		tlb.addRow("Size ", "25%", "50%", "75%", "25%", "50%", "75%");
		tlb.addRow("---", "----", "----", "----", "---", "----", "----");
		for (int i = 0; i < lstMean25.size(); i++) {
			MeanSDBean mean25 = lstMean25.get(i);
			MeanSDBean mean50 = lstMean50.get(i);
			MeanSDBean mean75 = lstMean75.get(i);
			if (isZeroClass) {
				double[] zero25 = mean25.getZeroClass();
				double[] zero50 = mean50.getZeroClass();
				double[] zero75 = mean75.getZeroClass();
				tlb.addRow(mean25.getAttrName(), "" + str4digit(zero25[0]), ""
						+ str4digit(zero50[0]), "" + str4digit(zero75[0]), ""
						+ str4digit(zero25[1]), "" + str4digit(zero50[1]), ""
						+ str4digit(zero75[1]));
			} else {
				double[] one25 = mean25.getOneClass();
				double[] one50 = mean50.getOneClass();
				double[] one75 = mean75.getOneClass();
				tlb.addRow(mean25.getAttrName(), "" + str4digit(one25[0]), ""
						+ str4digit(one50[0]), "" + str4digit(one75[0]), ""
						+ str4digit(one25[1]), "" + str4digit(one50[1]), ""
						+ str4digit(one75[1]));
			}
		}

		System.out.println(tlb);
	}
}
