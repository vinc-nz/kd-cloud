package com.kdcloud.gwt.client;

import com.kdcloud.server.entity.Dataset;

public class Controller {
	
	DetailsPanel detailsPanel;
	
	Scheduler scheduler;
	
	public Controller(DetailsPanel detailsPanel, Scheduler scheduler) {
		super();
		this.detailsPanel = detailsPanel;
		this.scheduler = scheduler;
	}



	public void onDatasetSelected(Dataset dataset) {
		detailsPanel.setDataset(dataset);
	}



	public void onApplicationSelected(String value) {
		// TODO Auto-generated method stub
		
	}
	
}
