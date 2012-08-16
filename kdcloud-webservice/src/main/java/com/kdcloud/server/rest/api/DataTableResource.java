package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.Dataset;


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
	@Put
	public Long createDataset(String name, String description);
	
	@Get
	public ArrayList<Dataset> list();

}
