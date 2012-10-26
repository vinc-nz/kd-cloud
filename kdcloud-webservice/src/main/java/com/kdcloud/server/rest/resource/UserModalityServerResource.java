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

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.rest.api.UserModalityResource;

public class UserModalityServerResource extends FileServerResource implements
		UserModalityResource {
	

	public UserModalityServerResource() {
		super();
	}

	public UserModalityServerResource(Application application, String modalityId) {
		super(application, modalityId);
	}
	
	@Override
	public Modality getModality() {
		return (Modality) readObjectFromXml(Modality.class);
	}

	@Override
	public void saveModality(Document xml) {
		try {
			readObjectFromXml(xml, Modality.class); //validate xml
			byte[] content = serializeDom(xml);
			write(content);
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public String getPath() {
		return getActualUri(URI);
	}

	

}
