/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.client.gwt.mvc;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.kdcloud.server.domain.Dataset;

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
