package com.kku.ensemble;

import java.util.ArrayList;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;

public class DecsionTree implements BaseClassifer {
	List<Instance> zeroIns = new ArrayList<>();
	List<Instance> oneIns = new ArrayList<>();

	@Override
	public void train(Instances instance) {

		// find the best attribute
		int classIdx = instance.classIndex();
		for (int i = 0; i < instance.numInstances(); i++) {
			if (classIdx == 0) {
				zeroIns.add(instance.instance(i));
			} else {
				oneIns.add(instance.instance(i));
			}
		}
		
		
	}

	@Override
	public int[] predict(Instances instance) {
		return null;
	}

	@Override
	public int predict(Instance instance) {
		return 0;
	}

}
