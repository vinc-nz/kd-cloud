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

import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.lib.rest.api.DeviceResource;

public class DeviceServerResource extends KDServerResource implements DeviceResource {
	

	@Override
	@Put
	public void register(String regId) {
		user.getDevices().add(regId);
		getEntityMapper().save(user);
	}

	@Override
	@Post
	public void unregister(String regId) {
		user.getDevices().remove(regId);
		getEntityMapper().save(user);
	}

}
