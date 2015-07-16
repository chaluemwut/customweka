package com.kku.customnb;

import weka.core.Instance;
import weka.core.Instances;

public class RankNaiveBayes extends AbstractNaiveBayes {

	@Override
	public void train(Instances train) {

	}

	@Override
	public double predict(Instance test) {
		return 0;
	}

	@Override
	public double[] predict(Instances test) {
		return null;
	}

}
