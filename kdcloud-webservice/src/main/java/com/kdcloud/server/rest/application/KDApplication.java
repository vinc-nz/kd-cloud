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

import com.kdcloud.ext.rehab.paziente.CalibrationRestlet;
import com.kdcloud.ext.rehab.paziente.ComputeAnglesRestlet;
import com.kdcloud.ext.rehab.paziente.DownloadCompleteExerciseRestlet;
import com.kdcloud.ext.rehab.paziente.InsertBufferedDataRestlet;
import com.kdcloud.ext.rehab.paziente.InsertCompleteExerciseRestlet;
import com.kdcloud.ext.rehab.paziente.InsertDualModeSessionRestlet;
import com.kdcloud.ext.rehab.paziente.LoginRehabUserRestlet;
import com.kdcloud.ext.rehab.paziente.RehabUserRegistrationRestlet;
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
		
		Reflections reflections = new Reflections(
				"com.kdcloud.server.rest.resource");

		Set<Class<? extends KDServerResource>> allClasses = reflections
				.getSubTypesOf(KDServerResource.class);

		for (Class<? extends KDServerResource> clazz : allClasses) {
			try {
				getLogger().info("found resource " + clazz.getSimpleName());
				String uri = clazz.getField("URI").get(null).toString();
				router.attach(uri, clazz);
				getLogger().info("mapped uri " + uri);
			} catch (Exception e) {
				getLogger().info("could not map any uri to the class");
			}
		}
		
		//rehab tutor paziente restlet
		router.attach(LoginRehabUserRestlet.URI, LoginRehabUserRestlet.class);
		router.attach(DownloadCompleteExerciseRestlet.URI, DownloadCompleteExerciseRestlet.class);
		router.attach(CalibrationRestlet.URI, CalibrationRestlet.class);
		router.attach(ComputeAnglesRestlet.URI, ComputeAnglesRestlet.class);
		router.attach(InsertDualModeSessionRestlet.URI, InsertDualModeSessionRestlet.class);
		router.attach(InsertBufferedDataRestlet.URI, InsertBufferedDataRestlet.class);
		router.attach(InsertCompleteExerciseRestlet.URI, InsertCompleteExerciseRestlet.class);
		
		router.attach(RehabUserRegistrationRestlet.URI, RehabUserRegistrationRestlet.class);
		
		//OLD
//		router.attach(InsertDataRestlet.URI, InsertDataRestlet.class);
//		router.attach(InsertAnglesRestlet.URI, InsertAnglesRestlet.class);
//		router.attach(DownloadExerciseRestlet.URI, DownloadExerciseRestlet.class);		
//		router.attach(DeleteAllRestlet.URI, DeleteAllRestlet.class);		
//		router.attach(InsertExerciseRestlet.URI, InsertExerciseRestlet.class);			
//		router.attach(NumberOfExercisesRestlet.URI, NumberOfExercisesRestlet.class);
		
		
		
		
		
		
		
		return router;
	}

}
