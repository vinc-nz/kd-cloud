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

	public void add(ViewComponent c) {
		components.add(c);
	}

}
