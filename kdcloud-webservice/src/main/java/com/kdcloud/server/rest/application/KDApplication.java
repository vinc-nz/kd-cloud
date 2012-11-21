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

import com.kdcloud.ext.rehab.paziente.DeleteAllRestlet;
import com.kdcloud.ext.rehab.paziente.TestCalcolaAngoliOnlyRestlet;
import com.kdcloud.ext.rehab.paziente.TestCalibrationRestlet;
import com.kdcloud.ext.rehab.paziente.TestDownloadDataPacketProvaRestlet;
import com.kdcloud.ext.rehab.paziente.DownloadEsercizioRestlet;
import com.kdcloud.ext.rehab.paziente.InsertAngoliRestlet;
import com.kdcloud.ext.rehab.paziente.InsertDataRestlet;
import com.kdcloud.ext.rehab.paziente.InsertDualModeSessionRestlet;
import com.kdcloud.ext.rehab.paziente.InsertEsercizioRestlet;
import com.kdcloud.ext.rehab.paziente.LoginPazienteRestlet;
import com.kdcloud.ext.rehab.paziente.NumeroEserciziRestlet;
import com.kdcloud.ext.rehab.paziente.RegistraPazienteRestlet;
import com.kdcloud.ext.rehab.paziente.TestDownloadEsercizioCompletoRestlet;
import com.kdcloud.ext.rehab.paziente.TestInsertBufferedRawRestlet;
import com.kdcloud.ext.rehab.paziente.TestInsertEsercizioCompletoRestlet;
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
		router.attach(InsertDataRestlet.URI, InsertDataRestlet.class);
		router.attach(InsertAngoliRestlet.URI, InsertAngoliRestlet.class);
		router.attach(DownloadEsercizioRestlet.URI, DownloadEsercizioRestlet.class);		
		router.attach(DeleteAllRestlet.URI, DeleteAllRestlet.class);
		router.attach(InsertDualModeSessionRestlet.URI, InsertDualModeSessionRestlet.class);
		router.attach(InsertEsercizioRestlet.URI, InsertEsercizioRestlet.class);		
		router.attach(LoginPazienteRestlet.URI, LoginPazienteRestlet.class);
		router.attach(NumeroEserciziRestlet.URI, NumeroEserciziRestlet.class);
		router.attach(RegistraPazienteRestlet.URI, RegistraPazienteRestlet.class);
		router.attach(TestDownloadDataPacketProvaRestlet.URI, TestDownloadDataPacketProvaRestlet.class);
		router.attach(TestDownloadEsercizioCompletoRestlet.URI, TestDownloadEsercizioCompletoRestlet.class);
		router.attach(TestInsertEsercizioCompletoRestlet.URI, TestInsertEsercizioCompletoRestlet.class);
		router.attach(TestInsertBufferedRawRestlet.URI, TestInsertBufferedRawRestlet.class);
		router.attach(TestCalcolaAngoliOnlyRestlet.URI, TestCalcolaAngoliOnlyRestlet.class);
		router.attach(TestCalibrationRestlet.URI, TestCalibrationRestlet.class);
		
		return router;
	}

}
