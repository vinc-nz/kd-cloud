package com.kdcloud.server.engine.embedded;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;

import com.kdcloud.weka.core.Attribute;
import com.kdcloud.weka.core.DenseInstance;
import com.kdcloud.weka.core.Instances;

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
	public boolean ready() {
		return mFile != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String filename = (String) config.get("filename");
		if (filename != null)
			loadFile(filename);
		return mFile != null;
	}
	
	private void loadFile(String filename) {
		try {
			URI uri = getClass().getClassLoader().getResource(filename).toURI();
			mFile = new File(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PortObject getOutput() {
		double[] sign = readData(mFile);
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(QRS.INPUT_ATTRIBUTE);
		Instances data = new Instances("file", attrs, 20000);
		for (int i = 0; i < sign.length; i++) {
			double[] row = { sign[i] };
			data.add(new DenseInstance(0, row));
		}
		return new BufferedInstances(data);
	}

}
