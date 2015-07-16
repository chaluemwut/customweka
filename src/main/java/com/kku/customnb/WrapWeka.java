package com.kku.customnb;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;

public class WrapWeka extends AbstractNaiveBayes {
	NaiveBayes nb = new NaiveBayes();
	public WrapWeka() {
		
	}
	
	@Override
	public void train(Instances train) {
		try {
			nb.buildClassifier(train);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public double predict(Instance test) {
		try {
			return nb.classifyInstance(test);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double[] predict(Instances test) {
		return null;
	}

}
