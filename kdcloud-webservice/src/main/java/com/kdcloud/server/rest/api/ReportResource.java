package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;


/**
 * URI /report/{id}
 * @author spax
 *
 */
public interface ReportResource {
	
	
	
	public static final String URI = "/report/{id}";

	
	/**
	 * retrives the report of the task with the given id
	 * @return
	 */
	@Get
	public Report retrive();

}
