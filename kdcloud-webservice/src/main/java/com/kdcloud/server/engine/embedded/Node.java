package com.kdcloud.server.engine.embedded;

import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;

import com.kdcloud.lib.domain.ServerParameter;

public interface Node extends Serializable {
	
	public void setInput(PortObject input) throws WrongConnectionException;
	
	public PortObject getOutput();
	
	public Set<ServerParameter> getParameters();
	
	public void configure(WorkerConfiguration config) throws WrongConfigurationException;
	
	public void run(Logger logger) throws Exception;

}
