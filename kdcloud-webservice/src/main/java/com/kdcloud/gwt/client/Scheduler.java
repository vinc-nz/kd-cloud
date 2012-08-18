package com.kdcloud.gwt.client;

import com.google.gwt.core.client.GWT;
import com.kdcloud.server.entity.Dataset;

public class Scheduler {
	
	private final UserDataResourceProxy proxy = GWT
			.create(UserDataResourceProxy.class);
	
	String application = null;
	Dataset dataset = null;
	
	
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	

}
