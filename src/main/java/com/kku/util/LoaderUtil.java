package com.kku.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class LoaderUtil {

	private static String path_file = "/home/off/eclipse/weka/eclipse/workspace/naive-weka/src/main/java/com/kku/weka/";

	public static Instances load(String arffName) {
		try {
			String filePath = path_file + "data/arff/" + arffName;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
