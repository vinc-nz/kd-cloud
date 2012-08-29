package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.ServerParameter;

/**
 * URI /process/ecg/{id}
 * @author spax
 *
 */
public interface SchedulerResource {
	
	
	public static final String URI = "/process/ecg/" + ServerParameter.DATASET_ID;
	
	
	/**
	 * request the processing of the dataset with the given id
	 * @return the id of the task enqueued for the processing
	 */
	@Get
	public Long requestProcess();

}
