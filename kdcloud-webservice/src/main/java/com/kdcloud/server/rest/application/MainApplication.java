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
package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Filter;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import com.kdcloud.server.rest.resource.EngineServerResource;

public class MainApplication extends Application {
	
	public static final String WORKER_URI = "/_exec/{id}";
	
	@Override
	public Restlet createInboundRoot() {
		getLogger().setLevel(Level.INFO);
		
		Context applicationContext = new GAEContext(getLogger());
		
		setContext(applicationContext);

		Router router = new Router(getContext());
		
		router.attach(WORKER_URI, EngineServerResource.class);
		
		Application kdApplication = new KDApplication(applicationContext);
		
		ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "testRealm");
		guard.setVerifier(new OAuthVerifier(getLogger(), true));
		
		Filter core = new Filter() {
			@Override
			protected int beforeHandle(Request request, Response response) {
				if (!request.getProtocol().equals(Protocol.HTTPS)) {
					String target = "https://" + request.getHostRef().getHostDomain() + request.getResourceRef().getPath();
	                Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_SEE_OTHER);
	                redirector.handle(request, response);
	                return STOP;
				}
				return CONTINUE;
			}
		};

		
		core.setNext(guard);
		guard.setNext(kdApplication);
		
		router.attachDefault(core);

		return router;

	}

}
