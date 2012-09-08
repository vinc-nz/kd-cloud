package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;

public abstract class NodeAdapter implements Node {

	@Override
	public void setInput(PortObject input) throws WrongConnectionException {
		
	}

	@Override
	public PortObject getOutput() {
		return null;
	}

	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>();
	}

	@Override
	public void configure(WorkerConfiguration config)
			throws WrongConfigurationException {
		
	}

	@Override
	public void run() throws Exception {
		
	}
	
	

}
