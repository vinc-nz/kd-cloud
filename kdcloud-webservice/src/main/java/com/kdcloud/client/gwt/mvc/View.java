package com.kdcloud.client.gwt.mvc;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.kdcloud.server.entity.Dataset;

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
	
	public boolean askDatasetRemovalConfirm(Dataset d) {
		String message = "Are you sure do delete %s?";
		return Window.confirm(message.replace("%s", d.getName()));
	}
	
	public String askCommitterEmail() {
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";
		String email = "email";
		boolean valid = false;
		while(!valid) {
			email = Window.prompt("Enter contributor's email address", email);
			if (email.matches(emailPattern))
				valid = true;
			else
				Window.alert("not a valid email address!");
		}
		return email;
	}

}
