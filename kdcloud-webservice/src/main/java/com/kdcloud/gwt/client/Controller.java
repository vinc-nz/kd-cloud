package com.kdcloud.gwt.client;

import com.kdcloud.server.entity.Dataset;

public class Controller {
	
	DetailsPanel detailsPanel;
	
	Scheduler scheduler;
	
	public void onDatasetSelected(Dataset dataset) {
		detailsPanel.setDataset(dataset);
	}
	
}
