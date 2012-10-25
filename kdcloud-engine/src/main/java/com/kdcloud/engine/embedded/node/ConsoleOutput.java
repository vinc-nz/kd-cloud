package com.kdcloud.engine.embedded.node;

import weka.core.converters.CSVSaver;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WrongInputException;

public class ConsoleOutput extends NodeAdapter {
	
	BufferedInstances input;
	
	@Override
	public void setInput(BufferedInstances input) throws WrongInputException {
		this.input = input;
	}
	
	@Override
	public void run() throws Exception {
		CSVSaver saver = new CSVSaver();
		saver.setInstances(input.getInstances());
		saver.setDestination(System.out);
		saver.writeBatch();
	}

}
