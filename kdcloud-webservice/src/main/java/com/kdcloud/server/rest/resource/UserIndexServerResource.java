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
package com.kdcloud.server.rest.resource;

import java.lang.reflect.Method;
import java.util.Collection;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.UserIndex;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;

public class UserIndexServerResource extends KDServerResource {
	


	@SuppressWarnings("unchecked")
	@Get
	public UserIndex getIndex() {
		Group group = (Group) getEntityMapper().findByName(Group.class, getResourceIdentifier());
		if (group == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		String itemName = getResourceUri().replaceAll(".*/", "");
		String methodName = "get" + itemName.substring(0, 1).toUpperCase() + itemName.substring(1);
		try {
			Method m = Group.class.getMethod(methodName, User.class);
			Collection<String> names = (Collection<String>) m.invoke(group, user);
			return new UserIndex(names);
		} catch (Exception e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}

}
