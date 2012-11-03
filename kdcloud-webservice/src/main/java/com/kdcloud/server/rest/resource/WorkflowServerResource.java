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
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.server.entity.StoredWorkflow;
import com.kdcloud.server.rest.application.ConvertUtils;

public class WorkflowServerResource extends BasicServerResource<StoredWorkflow>  {
	
	public static final String URI = "/workflow/{id}";
	
	KDEngine engine;

	public WorkflowServerResource() {
	}

	WorkflowServerResource(Application application, String workflowId) {
		super(application, workflowId);
	}
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = (KDEngine) inject(KDEngine.class);
	}

	@Put
	@Override
	public void createOrUpdate(Representation representation) {
		super.createOrUpdate(representation);
	}

	@Get
	public Document getWorkflow() {
		InputStream is = read().readWorkflow();
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error reading workflow", e);
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
		}
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
			byte[] workflow = ConvertUtils.toByteArray(representation);
			resource.setContent(workflow);
			engine.getWorker(resource.readWorkflow()); // validate workflow
		} catch (IOException e) {
			getLogger().log(Level.INFO, "unable to read workflow", e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
