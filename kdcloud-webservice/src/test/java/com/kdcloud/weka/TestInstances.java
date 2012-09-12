package com.kdcloud.weka;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instances;

public class TestInstances {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Instances data = new Instances("test", new ArrayList<Attribute>(), 0);
	}

}
