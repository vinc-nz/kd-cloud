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
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.lib.rest.api.DeviceResource;

public class DeviceServerResource extends KDServerResource implements DeviceResource {
	
	public DeviceServerResource() {
		super();
	}

	DeviceServerResource(Application application) {
		super(application, null);
	}

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
