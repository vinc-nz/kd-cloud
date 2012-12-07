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

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.UserIndex;
import com.kdcloud.lib.rest.api.SubscribersResource;
import com.kdcloud.server.entity.Group;

public class SubscribersServerResource extends KDServerResource implements
		SubscribersResource {
	
	
	public SubscribersServerResource() {
		super();
	}

	SubscribersServerResource(Application application, String groupName) {
		super(application, groupName);
	}


	@Override
	public UserIndex getIndex() {
		Group group = (Group) getEntityMapper().findByName(Group.class, getResourceIdentifier());
		if (group == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		return new UserIndex(group.getSubscribedUsers());
	}

}
