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

import org.restlet.Application;
import org.restlet.data.Status;
import org.w3c.dom.Document;

import com.kdcloud.lib.rest.api.ViewResource;

public class ViewServerResource extends FileServerResource implements
		ViewResource {
	
	public ViewServerResource() {
		super();
	}

	public ViewServerResource(Application application, String viewId) {
		super(application, viewId);
	}
	
	@Override
	public Document getView() {
		Document d = serveStaticXml();
		if (d != null)
			return d;
		return (Document) readObject();
	}

	@Override
	public void saveView(Document view) {
		try {
			byte[] bytes = serializeDom(view);
			write(bytes);
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public String getPath() {
		return getActualUri(URI).substring(1);
	}

	

}
