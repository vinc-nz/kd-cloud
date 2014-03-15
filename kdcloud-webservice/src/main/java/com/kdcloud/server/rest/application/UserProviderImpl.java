/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.application;

import org.restlet.Request;

import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.EntityMapper;

public class UserProviderImpl implements UserProvider {

	public String getUserId(Request request) {
		if (request.getClientInfo().getUser() == null)
			return null;
		return request.getClientInfo().getUser().getIdentifier();
	}

	@Override
	public User getUser(Request request, EntityMapper entityMapper) {
		String id = getUserId(request);
		if (id != null) {
			User user = (User) entityMapper.findByName(User.class, id);
			if (user == null) {
				user = new User(id);
				entityMapper.save(user);
			}
			return user;
		}
		return null;
	}


}
