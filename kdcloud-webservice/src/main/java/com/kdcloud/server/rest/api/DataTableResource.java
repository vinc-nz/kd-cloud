package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;


/**
 * URI /data
 * @author vincenzo
 *
 */
public interface DataTableResource {
	
	
	
	public static final String URI = "/data";
	
	
	/**
	 * creates a new dataset
	 * @return the dataset id
	 */
	@Get
	public Long createDataset();

}
