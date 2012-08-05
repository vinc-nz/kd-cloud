package com.kdcloud.server.rest.api;

import java.util.LinkedList;


import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataRow;

/**
 * URI /data/{id}
 * @author vincenzo
 *
 */
public interface DataRowResource {
	
	
	public static final String URI = "/data/{id}";
	
	/**
	 * uploads data to the dataset with the given id
	 * @param values the data
	 */
	@Put
	public void uploadData(LinkedList<DataRow> data);

}
