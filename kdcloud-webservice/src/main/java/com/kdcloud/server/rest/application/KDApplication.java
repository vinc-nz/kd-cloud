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
package com.kdcloud.server.rest.application;

import java.util.Set;

import org.reflections.Reflections;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.kdcloud.server.rest.resource.KDServerResource;

public class KDApplication extends Application {
	
	public KDApplication(Context context) {
		super(context);
		getLogger().info("init database");
	}

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());
		router.attach("/test", new TestRestlet());
		
		Reflections reflections = new Reflections(
				"com.kdcloud.server.rest.resource");

		Set<Class<? extends KDServerResource>> allClasses = reflections
				.getSubTypesOf(KDServerResource.class);

		for (Class<? extends KDServerResource> clazz : allClasses) {
			try {
				String uri = clazz.getField("URI").get(null).toString();
				router.attach(uri, clazz);
				getLogger().info("mapped uri " + uri);
			} catch (Exception e) {

			}
		}

//		router.attach(UserDataResource.URI, UserDataServerResource.class);
//		router.attach(DatasetResource.URI, DatasetServerResource.class);
//		router.attach(AnalysisResource.URI, AnalysisServerResource.class);
//		router.attach(SchedulerResource.URI, SchedulerServerResource.class);
//		router.attach(ReportResource.URI, ReportServerResource.class);
//		router.attach(DeviceResource.URI, DeviceServerResource.class);
//		router.attach(UserDetailsResource.URI, UserDetailsServerResource.class);
//		router.attach(ModalitiesResource.URI, ModalitiesServerResource.class);
//		router.attach(GlobalAnalysisResource.URI,
//				GlobalAnalysisServerResource.class);
//		router.attach(GlobalDataResource.URI, GlobalDataServerResource.class);

		return router;
	}

}
