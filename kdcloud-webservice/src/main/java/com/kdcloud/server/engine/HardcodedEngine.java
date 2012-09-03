package com.kdcloud.server.engine;

import com.kdcloud.server.entity.Workflow;
import com.kdcloud.weka.core.Instances;
import com.kdcloud.weka.core.UnsupportedAttributeTypeException;

public class HardcodedEngine implements KDEngine {

	@Override
	public Instances execute(Instances dataset, Workflow workflow) throws Exception {
		Instances schema = new Instances("schema", workflow.getInputSpec(), 0);
		String diffMsg = dataset.equalHeadersMsg(schema);
		if (diffMsg != null)
			throw new UnsupportedAttributeTypeException(diffMsg);
		String className = (String) workflow.getExecutionData();
		Class<?> clazz = Class.forName(className);
		KDCommand command = clazz.asSubclass(KDCommand.class).getConstructor().newInstance();
		return command.execute(dataset);
	}
	
}
