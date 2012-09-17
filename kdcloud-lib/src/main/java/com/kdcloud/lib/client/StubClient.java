package com.kdcloud.lib.client;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.kdcloud.lib.domain.InputSpecification;

import weka.core.DenseInstance;
import weka.core.Instances;

public class StubClient extends Client {

	
	public StubClient(String url) throws ParserConfigurationException {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void log(String message, Throwable thrown) {
		// TODO Auto-generated method stub

	}

	@Override
	public Instances getData() {
		double[] cells = { 1, 2 };
		Instances data = new Instances(InputSpecification.newInstances("test", 2));
		data.add(new DenseInstance(0, cells));
		return data;
	}

	@Override
	public String handleChoice(String[] choices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void report(Document view) {
		System.out.println("this is a report");
	}
	
	public static void main(String[] args) throws ParserConfigurationException {
		Client kdcloud = new StubClient("http://localhost:8888");
		
	}

}
