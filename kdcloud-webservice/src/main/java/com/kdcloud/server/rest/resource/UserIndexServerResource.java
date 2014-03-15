/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.server.rest.resource;

import java.util.Collection;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.UserIndex;
import com.kdcloud.server.entity.Group;

public class UserIndexServerResource extends KDServerResource {
	


	@Get
	public UserIndex getIndex() {
		Group group = getEntityMapper().findByName(Group.class, getResourceIdentifier());
		if (group == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		String property = getResourceUri().replaceAll(".*/", "");
		Collection<String> names = group.getProperty(property, user);
		if (names == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		return new UserIndex(names);
	}

}
