package com.kdcloud.server.rest.api;

import java.util.ArrayList;
import java.util.LinkedList;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.weka.core.Instances;

/**
 * URI /data/{id}
 * @author vincenzo
 *
 */
public interface DatasetResource {
	
	
	public static final String URI = "/data/" + ServerParameter.DATASET_ID;
	
	/**
	 * uploads data to the dataset with the given id
	 * @param values the data
	 */
	@Put
	public void uploadData(Instances data);
	
	@Post
	public void addCommitter(String email);
	
	@Delete
	public void deleteDataset();
	
	@Get
	public Instances getData();

}
