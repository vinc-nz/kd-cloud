package com.kdcloud.lib.rest.api;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.lib.domain.ServerParameter;

/**
 * URI /report/{id}
 * @author spax
 *
 */
public interface ReportResource {
	
	
	
	public static final String URI = "/report/" + ServerParameter.TASK_ID;

	
	/**
	 * retrives the report of the task with the given id
	 * @return
	 */
	@Get
	public Representation retrive();

}
