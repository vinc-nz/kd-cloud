package com.kdcloud.gwt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserPanel extends VerticalPanel implements ViewComponent {
	
	Model model;
	
	Label label;

	public UserPanel(Model model) {
		super();
		this.model = model;
		this.label = new Label("you are not logged");
		this.add(label);
	}

	@Override
	public void setupHandlers(Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		label.setText("you are logged with token " + model.user);
	}

}
