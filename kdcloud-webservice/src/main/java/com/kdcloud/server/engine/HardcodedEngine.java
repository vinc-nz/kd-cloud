package com.kdcloud.server.engine;

import com.kdcloud.server.entity.Workflow;
import com.kdcloud.weka.core.Instances;
import com.kdcloud.weka.core.UnsupportedAttributeTypeException;

public class HardcodedEngine implements KDEngine {

	@Override
	public Instances execute(Instances dataset, Workflow workflow) throws Exception {
		if (!validInput(dataset, workflow))
			throw new UnsupportedAttributeTypeException();
		String className = (String) workflow.getExecutionData();
		Class<?> clazz = Class.forName(className);
		HardcodedAlgorithm command = clazz.asSubclass(HardcodedAlgorithm.class).getConstructor().newInstance();
		return command.execute(dataset);
	}

	@Override
	public boolean validInput(Instances input, Workflow workflow) {
		Instances schema = new Instances("schema", workflow.getInputSpec(), 0);
		return input.equalHeaders(schema);
	}
	
}
