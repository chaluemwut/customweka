package com.kku.ranknb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.kku.customnb.MeanSDBean;
import com.kku.customnb.ProbBean;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public abstract class AbstractNaiveBayes implements NaiveBayes {
	List<DescriptiveStatistics> oneStat;
	List<DescriptiveStatistics> zeroStat;
	List<ProbBean> predictionReport;
	
	int numAttr;
	Instances train;
	int classIndex;
	int startAttr;
	int endAttr;
	int numZeroClass;
	int numOneClass;

	double oneAllProb;
	double zeroAllProb;

	double oneClassProb;
	double zeroClassProb;

	protected List<DescriptiveStatistics> convertToStat(int numAttr,
			double[][] posIns) {
		List<DescriptiveStatistics> retStat = new ArrayList<>();
		for (int i = 0; i < numAttr; i++) {
			DescriptiveStatistics desc = new DescriptiveStatistics(posIns[i]);
			retStat.add(desc);
		}
		return retStat;
	}

	protected double[][] converToArray(int numAttr, List<Instance> positive) {
		double[][] ret = new double[numAttr][positive.size()];
		for (int i = 0; i < numAttr; i++) {
			for (int j = 0; j < positive.size(); j++) {
				Instance ins = positive.get(j);
				ret[i][j] = ins.value(i);
			}
		}
		return ret;
	}

	@Override
	public List<MeanSDBean> getTrainingReport() {
		List<MeanSDBean> ret = new ArrayList<>();
		Instance firstIns = train.firstInstance();
		for (int i = startAttr; i < endAttr; i++) {
			Attribute attr = firstIns.attribute(i);
			DescriptiveStatistics oneDesc = oneStat.get(i);
			DescriptiveStatistics zeroDesc = zeroStat.get(i);
			double[] oneClass = new double[2];
			oneClass[0] = oneDesc.getMean();
			oneClass[1] = oneDesc.getStandardDeviation();

			double[] zeroClass = new double[2];
			zeroClass[0] = zeroDesc.getMean();
			zeroClass[1] = zeroDesc.getStandardDeviation();

			MeanSDBean bean = new MeanSDBean();
			bean.setAttrName(attr.name());
			bean.setOneClass(oneClass);
			bean.setZeroClass(zeroClass);
			ret.add(bean);
		}
		return ret;
	}
	

	@Override
	public double[] predict(Instances test) {
		double[] ret = new double[test.numInstances()];
		for (int i = 0; i < test.numInstances(); i++) {
			ret[i] = predict(test.instance(i));
		}
		return ret;
	}

	@Override
	public List<ProbBean> getPredictReport() {
		return predictionReport;
	}

	@Override
	public int[] getNumClass() {
		return new int[] { numZeroClass, numOneClass };
	}

	public double getOneAllProb() {
		return oneAllProb;
	}

	public void setOneAllProb(double oneAllProb) {
		this.oneAllProb = oneAllProb;
	}

	public double getZeroAllProb() {
		return zeroAllProb;
	}

	public void setZeroAllProb(double zeroAllProb) {
		this.zeroAllProb = zeroAllProb;
	}

	public double getOneClassProb() {
		return oneClassProb;
	}

	public void setOneClassProb(double oneClassProb) {
		this.oneClassProb = oneClassProb;
	}

	public double getZeroClassProb() {
		return zeroClassProb;
	}

	public void setZeroClassProb(double zeroClassProb) {
		this.zeroClassProb = zeroClassProb;
	}

	
}
