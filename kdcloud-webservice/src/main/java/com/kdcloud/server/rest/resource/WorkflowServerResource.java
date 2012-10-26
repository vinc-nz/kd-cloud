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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import weka.core.Instances;

import com.kdcloud.lib.rest.api.WorkflowResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {


	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, String workflowId) {
		super(application, workflowId);
	}
	

	@Override
	public Representation execute(Form form) {
		InputStream	workflow = getClass().getClassLoader().getResourceAsStream(getPath());
		if (workflow == null)
			workflow = read();
		if (workflow != null) {
			Instances data;
			try {
				data = execute(form, workflow);
			} catch (IOException e) {
				getLogger().log(Level.SEVERE, e.getMessage(), e);
				setStatus(Status.SERVER_ERROR_INTERNAL);
				return null;
			}
			if (data != null && !data.isEmpty()) {
				getLogger().info("sending " + data.size() + " instances");
				return new InstancesRepresentation(MediaType.TEXT_CSV, data);
			}
		}
		return null;
	}

	@Override
	public void putWorkflow(Document dom) {
		try {
			byte[] bytes = serializeDom(dom);
			InputStream is = new ByteArrayInputStream(bytes);
			engine.getWorker(is); //validate workflow
			write(bytes);
		} catch (Exception e) {
			getLogger().log(Level.INFO, "unable to read workflow", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public Document getWorkflow() {
		return readDom();
	}

	@Override
	public String getPath() {
		return getActualUri(URI).substring(1);
	}

}
