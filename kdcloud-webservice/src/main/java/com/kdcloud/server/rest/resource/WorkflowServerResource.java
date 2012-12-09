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

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.application.ConvertHelper;

public class WorkflowServerResource extends BasicServerResource<Workflow>  {
	
	public static final String URI = "/workflow/{id}";
	
	KDEngine engine;

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = inject(KDEngine.class);
	}

	@Put
	@Override
	public void createOrUpdate(Representation representation) {
		super.createOrUpdate(representation);
	}
	
	@Delete
	@Override
	public void remove() {
		super.remove();
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
	public Workflow find() {
		return getEntityMapper().findByName(Workflow.class, getResourceIdentifier());
	}

	@Override
	public void save(Workflow e) {
		getEntityMapper().save(e);
	}

	@Override
	public void delete(Workflow e) {
		getEntityMapper().delete(e);
	}

	@Override
	public Workflow create() {
		Workflow stored = new Workflow();
		stored.setName(getResourceIdentifier());
		stored.setOwner(user);
		return stored;
	}

	@Override
	public void update(Workflow entity, Representation representation) {
		byte[] workflow = ConvertHelper.toByteArray(representation);
		entity.setContent(workflow);
		try {
			engine.getWorker(entity.readWorkflow()); // validate workflow
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		} 
	}

}
