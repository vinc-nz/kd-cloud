package com.kdcloud.server.rest.api;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import weka.core.Instances;

import com.kdcloud.server.domain.ServerParameter;

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
	public void uploadData(Representation representation);
	
	@Post
	public void addCommitter(String email);
	
	@Delete
	public void deleteDataset();
	
	@Get
	public Instances getData();

}
