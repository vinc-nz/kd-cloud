package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;

/**
 * URI /process/ecg/{id}
 * @author spax
 *
 */
public interface SchedulerResource {
	
	
	public static final String URI = "/process/ecg/{id}";
	public static final String PARAM_ID = "id";
	
	
	/**
	 * request the processing of the dataset with the given id
	 * @return the id of the task enqueued for the processing
	 */
	@Get
	public Long requestProcess();

}
