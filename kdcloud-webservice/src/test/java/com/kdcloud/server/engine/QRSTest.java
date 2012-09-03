package com.kdcloud.server.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Vector;

import com.kdcloud.weka.core.Attribute;
import com.kdcloud.weka.core.DenseInstance;
import com.kdcloud.weka.core.Instances;

public class QRSTest {

	public static double[] readData(File file) {
		BufferedReader in = null;
		Vector<Double> sign = new Vector<Double>();
		try {
			in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			while (line != null) {
				// System.out.print(line+"\t");
				// System.out.println(Double.parseDouble(line));
				sign.add(Double.parseDouble(line));
				line = in.readLine();
			}
			;
			in.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double[] res = new double[sign.size()];
		for (int i = 0; i < sign.size(); i++) {
			res[i] = sign.get(i);
		}
		return res;
	}

	/**
	 * @param args
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException {
		URI uri = QRSTest.class.getClassLoader().getResource("ecg_test.txt")
				.toURI();
		double[] sign = readData(new File(uri));
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(new Attribute("sign"));
		Instances data = new Instances("test", attrs, 20000);
		for (int i = 0; i < sign.length; i++) {
			double[] row = { sign[i] };
			data.add(new DenseInstance(0, row));
		}
		Instances res = QRS.ecg(data);
		System.out.println(data.numInstances());
		System.out.println(res.numInstances());
	}

}
