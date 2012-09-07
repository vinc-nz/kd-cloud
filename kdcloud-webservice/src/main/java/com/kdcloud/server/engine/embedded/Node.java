package com.kdcloud.server.engine.embedded;

import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;

public interface Node {
	
	public boolean setInput(PortObject input);
	
	public boolean ready();
	
	public boolean configure(WorkerConfiguration config);
	
	public Set<ServerParameter> getParameters();
	
	public PortObject getOutput();
	
	public void run();

}
