package com.kdcloud.server.rest.api;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;

import weka.core.Instances;


/**
 * URI /data
 * @author vincenzo
 *
 */
public interface UserDataResource {
	
	
	public static final String URI = "/data";
	
	
	/**
	 * creates a new dataset
	 * @return the dataset id
	 */
	@Put
	public Long createDataset(Representation representation);
	
	
	@Delete
	public void deleteAllData();

}
