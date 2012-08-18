package com.kdcloud.gwt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kdcloud.server.entity.Dataset;

public class DetailsPanel extends VerticalPanel {
	
	public void setDataset(Dataset dataset) {
		this.clear();
		this.add(new Label("<h2>"+dataset.getName()+"</h2>"));
	}

}
