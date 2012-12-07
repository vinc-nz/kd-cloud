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

import java.util.HashMap;

import org.restlet.Context;
import org.restlet.Request;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.DataMapperFactory;
import com.kdcloud.server.persistence.gae.JunitMapperFactory;
import com.kdcloud.server.rest.application.UserProvider;

public class TestContext extends Context {

	public static final String USER_ID = "tester";

	public TestContext() {
		HashMap<String, Object> attrs = new HashMap<String, Object>();

		attrs.put(DataMapperFactory.class.getName(),
				new JunitMapperFactory());

		

		attrs.put(KDEngine.class.getName(), new EmbeddedEngine());

		attrs.put(UserProvider.class.getName(), new UserProvider() {

			@Override
			public User getUser(Request request, EntityMapper entityMapper) {
				return new User(USER_ID);
			}
		});

		this.setAttributes(attrs);

	}

}
