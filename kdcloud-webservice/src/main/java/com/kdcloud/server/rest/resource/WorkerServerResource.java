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

import java.io.IOException;
import java.io.InputStream;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import weka.core.Instances;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.Worker;
import com.kdcloud.engine.embedded.node.UserDataReader;
import com.kdcloud.server.persistence.PersistenceContext;

public class WorkerServerResource extends KDServerResource {

	KDEngine engine;

	public WorkerServerResource() {
		super();
	}


	WorkerServerResource(Application application, String resourceIdentifier) {
		super(application, resourceIdentifier);
	}


	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = (KDEngine) inject(KDEngine.class);
	}

	public Instances execute(Form form, InputStream input) throws IOException {
		Worker worker = engine.getWorker(input);
		worker.setParameter(PersistenceContext.class.getName(), getPersistenceContext());
		worker.setParameter(UserDataReader.ANALYST_ID, user.getName());
		for (String param : worker.getParameters()) {
			String value = form.getFirstValue(param);
			getLogger().info(
					"setting parameter: " + param + "=" + value);
			worker.setParameter(param, value);
		}
		if (worker.configure())
			worker.run();
		if (worker.getStatus() == Worker.STATUS_JOB_COMPLETED) {
			return worker.getOutput();
		} else {
			throw new ResourceException(Status.CLIENT_ERROR_PRECONDITION_FAILED);
		}
	}

}
