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
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import weka.core.Instances;

import com.kdcloud.lib.rest.api.WorkflowResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.StoredWorkflow;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {

	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, String workflowId) {
		super(application, workflowId);
	}

	@Override
	public Representation execute(Form form) {
		InputStream	workflow = getStream();
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
	public void putWorkflow(Representation representation) {
		createOrUpdate(representation);
	}

	@Override
	public Document getWorkflow() {
		InputStream is = read().readWorkflow();
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		} catch (Exception e) {
			throw new ResourceException(e);
		}
	}

	
	public InputStream getStream() {
		String path = "workflow/" + getResourceIdentifier();
		InputStream	stream = getClass().getClassLoader().getResourceAsStream(path);
		if (stream != null)
			return stream;
		StoredWorkflow stored = find();
		if (stored != null)
			return stored.readWorkflow();
		return null;
	}

	@Override
	public StoredWorkflow find() {
		return (StoredWorkflow) getPersistenceContext().findByName(
				StoredWorkflow.class, getResourceIdentifier());
	}

	@Override
	public void save(StoredWorkflow e) {
		getPersistenceContext().save(e);
	}

	@Override
	public void delete(StoredWorkflow e) {
		getPersistenceContext().delete(e);
	}

	@Override
	public StoredWorkflow create() {
		StoredWorkflow stored = new StoredWorkflow();
		stored.setName(getResourceIdentifier());
		return stored;
	}

	@Override
	public void update(StoredWorkflow resource, Representation representation) {
		try {
			if (resource.writeWorkflow(representation.getStream())) {
				engine.getWorker(resource.readWorkflow()); // validate workflow
			}
		} catch (IOException e) {
			getLogger().log(Level.INFO, "unable to read workflow", e);
		}
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
	}

}
