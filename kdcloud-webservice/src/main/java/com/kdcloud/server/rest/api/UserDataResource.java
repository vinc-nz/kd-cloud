package com.kdcloud.server.rest.api;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.Dataset;


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
	public Long createDataset(Dataset dto);
	
//	@Get
//	public ArrayList<Dataset> listDataset();
	@Get
	public Long createDataset();
	
	@Delete
	public void deleteAllData();

}
