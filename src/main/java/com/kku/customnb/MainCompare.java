package com.kku.customnb;

import com.kku.weka.BaseComputation;

public class MainCompare extends BaseComputation {

	public void processCompare() {
		NaiveBayes[] nbList = new NaiveBayes[] { new RankNaiveBayes(),
				new WrapWeka() };
		for (int i = 0; i < nbList.length; i++) {
			
		}
	}

	public static void main(String[] args) {
		MainCompare main = new MainCompare();
		main.processCompare();
	}

}
