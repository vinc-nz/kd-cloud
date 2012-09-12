package com.kdcloud.server.engine;

import java.util.Set;

import com.kdcloud.server.domain.Report;
import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.persistence.PersistenceContext;

public interface Worker extends Runnable {
	
	public static final int STATUS_JOB_COMPLETED = 0;
	public static final int STATUS_WAITING_CONFIGURATION = 1;
	public static final int STATUS_ERROR_WRONG_CONFIG = 2;
	public static final int STATUS_ERROR_WRONG_INPUT = 3;
	public static final int STATUS_ERROR_RUNTIME = 4;
	public static final int STATUS_READY = 5;
	
	public int getStatus();

	public void setPersistenceContext(PersistenceContext pc);

	public void setParameter(ServerParameter param, String value);

	public Set<ServerParameter> getParameters();
	
	public boolean configure();

	public Report getReport();

}
