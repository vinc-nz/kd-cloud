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

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.data.LocalReference;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.rest.application.UserProvider;

public abstract class KDServerResource extends ServerResource {

	private PersistenceContext persistenceContext;
	private UserProvider userProvider;
	private String resourceIdentifier;

	User user;

	public KDServerResource() {
		
	}

	KDServerResource(Application application, String resourceIdentifier) {
		setApplication(application);
		Request req = new Request();
		req.setResourceRef(new LocalReference(resourceIdentifier));
		req.setProtocol(Protocol.CLAP);
		setRequest(req);
		doInit();
		this.resourceIdentifier = resourceIdentifier;
		this.user = userProvider.getUser(null, persistenceContext);
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		
		//FG
		CopyOnWriteArraySet<Method> s = new CopyOnWriteArraySet<Method>();
		s.add(Method.PUT);
		s.add(Method.POST);
		s.add(Method.GET);
		setAllowedMethods(s);
		
		
		
		userProvider = (UserProvider) inject(UserProvider.class);

		PersistenceContextFactory pcf = (PersistenceContextFactory) inject(PersistenceContextFactory.class);
		persistenceContext = pcf.get();
	}

	protected String getResourceIdentifier() {
		if (resourceIdentifier != null)
			return resourceIdentifier;
		return (String) getRequestAttributes().get("id");
	}
	
	protected String getResourceUri() {
		return getReference().toString().replace(getHostRef().toString(), "");
	}
	
	protected Representation fetchLocally() {
		return fetchLocalResource(getResourceUri().substring(1));
	}

	public Representation fetchLocalResource(String path) {
		LocalReference ref = new LocalReference(path);
		ref.setProtocol(Protocol.CLAP);
		return new ClientResource(ref).get();
	}

	public Representation doGet() {
		ClientResource cr = new ClientResource(getRequest().getResourceRef());
		cr.setChallengeResponse(getChallengeResponse());
		return cr.get();
	}

	@Override
	public Representation handle() {
		user = userProvider.getUser(getRequest(), persistenceContext);
		if (getMethod().equals(Method.GET))
			try {
				Representation local = fetchLocally();
				getLogger().info("found locally");
				getResponse().setEntity(local);
				return local;
			} catch (ResourceException e) {}
		return super.handle();
	}

	protected Object inject(Class<?> baseClass) {
		return getApplication().getContext().getAttributes()
				.get(baseClass.getName());
	}

	public PersistenceContext getPersistenceContext() {
		return persistenceContext;
	}

	public User getUser() {
		return user;
	}

}
