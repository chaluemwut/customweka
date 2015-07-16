package com.kku.ranknb;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class GeneralWrapWeka extends AbstractNaiveBayes {
	Classifier cls;

	public GeneralWrapWeka(Classifier cls) {
		this.cls = cls;
	}

	@Override
	public void train(Instances train) {
		try {
			cls.buildClassifier(train);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public double predict(Instance test) {
		try {
			return cls.classifyInstance(test);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String toString() {
		return cls.toString();
	}

}
