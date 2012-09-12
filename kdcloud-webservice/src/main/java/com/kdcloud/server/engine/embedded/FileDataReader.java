package com.kdcloud.server.engine.embedded;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class FileDataReader extends NodeAdapter {
	
	File mFile;
	
	public FileDataReader(String filename) {
		loadFile(filename);
	}
	
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


	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		String filename = (String) config.get("filename");
		if (filename != null)
			loadFile(filename);
		if (mFile == null)
			throw new WrongConfigurationException("input file not valid");
	}
	
	private void loadFile(String filename) {
		try {
			URI uri = getClass().getClassLoader().getResource(filename).toURI();
			mFile = new File(uri);
		} catch (Exception e) {
		}
	}

	@Override
	public PortObject getOutput() {
		double[] sign = readData(mFile);
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(new Attribute("sign"));
		Instances data = new Instances("file", attrs, 20000);
		for (int i = 0; i < sign.length; i++) {
			double[] row = { sign[i] };
			data.add(new DenseInstance(0, row));
		}
		return new BufferedInstances(data);
	}

}
