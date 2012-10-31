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

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import com.kdcloud.lib.rest.api.ViewResource;
import com.kdcloud.server.entity.View;

public class ViewServerResource extends BasicServerResource<View> implements ViewResource {
	
	public ViewServerResource() {
		super();
	}

	public ViewServerResource(Application application, String viewId) {
		super(application, viewId);
	}
	
	@Override
	public View find() {
		return (View) getPersistenceContext().findByName(View.class, getResourceIdentifier());
	}

	
	@Override
	public void save(View e) {
		getPersistenceContext().save(e);
	}

	@Override
	public void delete(View e) {
		getPersistenceContext().delete(e);
	}

	@Override
	@Get
	public Document getView() {
		return read().getSpecification();
	}

	@Override
	@Put
	public void saveView(Representation representation) {
		createOrUpdate(representation);
	}

	@Override
	public View create() {
		View v = new View();
		v.setName(getResourceIdentifier());
		return v;
	}

	@Override
	public void update(View resource, Representation representation) {
		DomRepresentation dom = new DomRepresentation(representation);
		try {
			resource.setSpecification(dom.getDocument());
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	

}
