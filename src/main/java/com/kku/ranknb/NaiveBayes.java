package com.kku.ranknb;

import java.util.List;

import com.kku.customnb.MeanSDBean;
import com.kku.customnb.ProbBean;

import weka.core.Instance;
import weka.core.Instances;

public interface NaiveBayes {

	public void train(Instances train);

	public double predict(Instance test);

	public double[] predict(Instances test);

	public List<MeanSDBean> getTrainingReport();
	
	public List<ProbBean> getPredictReport();
	
	public int[] getNumClass();
	

}
