package com.kdcloud.server.rest.api;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.ServerParameter;

/**
 * URI /process/ecg/{id}
 * @author spax
 *
 */
public interface SchedulerResource {
	
	
	public static final String URI = "/workflow/schedule/" + ServerParameter.WORKFLOW_ID;  
	
	
	/**
	 * request the processing of the dataset with the given id
	 * @return the id of the task enqueued for the processing
	 */
	@Post
	public Long requestProcess(Form form);

}
