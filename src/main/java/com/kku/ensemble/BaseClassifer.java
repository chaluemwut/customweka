package com.kku.ensemble;

import weka.core.Instance;
import weka.core.Instances;

public interface BaseClassifer {

	public void train(Instances instance);

	public int[] predict(Instances instance);

	public int predict(Instance instance);

}
