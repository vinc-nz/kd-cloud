package com.kdcloud.server.engine;

import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.persistence.PersistenceContext;

public interface Worker extends Runnable {

	public void setPersistenceContext(PersistenceContext pc);

	public void setParameter(ServerParameter param, String value);

	public Set<ServerParameter> getParameters();

	public void loadWorkflow(Workflow workflow) throws UnsupportedWorkflowException;

}
