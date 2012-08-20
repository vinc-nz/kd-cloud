package com.kdcloud.gwt.client;

import java.util.LinkedList;
import java.util.List;

public class View implements ViewComponent {
	
	List<ViewComponent> components = new LinkedList<ViewComponent>();
	
	@Override
	public void refresh() {
		for (ViewComponent c : components) {
			c.refresh();
		}
	}

	@Override
	public void setupHandlers(Controller controller) {
		for (ViewComponent c : components) {
			c.setupHandlers(controller);
		}
	}
	
	public void add(ViewComponent c) {
		components.add(c);
	}

}
