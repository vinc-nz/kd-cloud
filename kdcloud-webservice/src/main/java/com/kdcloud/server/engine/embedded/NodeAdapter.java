package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;

public abstract class NodeAdapter implements Node {
	
	@Override
	public boolean setInput(PortObject input) {
		return true;
	}
	
	@Override
	public boolean configure(WorkerConfiguration config) {
		return true;
	}
	
	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>();
	}
	
	@Override
	public PortObject getOutput() {
		return null;
	}
	
	@Override
	public void run() {
	}

}
