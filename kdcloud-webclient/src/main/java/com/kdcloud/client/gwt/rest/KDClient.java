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
package com.kdcloud.client.gwt.rest;

import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;

import com.google.gwt.core.client.GWT;
import com.kdcloud.rest.api.DatasetResource;
import com.kdcloud.rest.api.UserDataResource;
import com.kdcloud.rest.api.UserDetailsResource;

public class KDClient {
	
	UserDataResourceProxy userDataResourceProxy = GWT
			.create(UserDataResourceProxy.class);
	
	DatasetResourceProxy datasetResourceProxy = GWT.create(DatasetResourceProxy.class);
	
	UserDetailsResourceProxy userDetailsResourceProxy = GWT.create(UserDetailsResourceProxy.class);
	
	private ChallengeResponse auth;
	
	public KDClient() {
		userDataResourceProxy.getClientResource().setReference(UserDataResource.URI);
		userDetailsResourceProxy.getClientResource().setReference(UserDetailsResource.URI);
	}
	
	public void setToken(String token) {
		this.auth = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "gwt", token.toCharArray());
		this.userDataResourceProxy.getClientResource().setChallengeResponse(auth);
		this.datasetResourceProxy.getClientResource().setChallengeResponse(auth);
		this.userDetailsResourceProxy.getClientResource().setChallengeResponse(auth);
	}

	public UserDataResourceProxy getUserDataResource() {
		return userDataResourceProxy;
	}

	public DatasetResourceProxy getDatasetResource(Long id) {
		String uri = DatasetResource.URI.replaceAll("\\{\\w+\\}", Long.toString(id));
		datasetResourceProxy.getClientResource().setReference(uri);
		return datasetResourceProxy;
	}

	public UserDetailsResourceProxy getUserDetailsResource() {
		return userDetailsResourceProxy;
	}
	
	
	
}
