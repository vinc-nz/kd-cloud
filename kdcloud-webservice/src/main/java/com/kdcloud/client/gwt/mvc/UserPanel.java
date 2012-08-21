package com.kdcloud.client.gwt.mvc;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserPanel extends VerticalPanel implements ViewComponent {
	
	Model model;
	
	Label label;

	public UserPanel(Model model) {
		super();
		this.model = model;
		this.label = new Label("loading user info..");
		this.add(label);
		this.setSpacing(20);
	}

	@Override
	public void refresh() {
		label.setText("you are logged as " + model.user);
	}

}
