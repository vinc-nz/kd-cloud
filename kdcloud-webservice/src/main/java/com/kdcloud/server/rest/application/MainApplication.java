/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.routing.Filter;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import com.kdcloud.server.rest.resource.WorkflowServerResource;

public class MainApplication extends Application {
	
	public static final String WORKER_URI = "/_exec/{id}";
	
	@Override
	public Restlet createInboundRoot() {
		getLogger().setLevel(Level.INFO);
		
		Context applicationContext = new GAEContext(getLogger());
		
		setContext(applicationContext);

		Router router = new Router(getContext());
		
		router.attach(WORKER_URI, WorkflowServerResource.class);
		
		Application kdApplication = new KDApplication(applicationContext, createOutboundRoot());
		
		ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "testRealm");
		guard.setVerifier(new OAuthVerifier(getLogger(), true));
		
		Filter core = new Filter() {
			@Override
			protected int beforeHandle(Request request, Response response) {
				if (!request.getProtocol().equals(Protocol.HTTPS) && !isLocal(request.getHostRef())) {
					String target = "https://" + request.getHostRef().getHostDomain() + request.getResourceRef().getPath();
	                Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_SEE_OTHER);
	                redirector.handle(request, response);
	                return STOP;
				}
				return CONTINUE;
			}

			private boolean isLocal(Reference hostRef) {
				return hostRef.getHostDomain().contains("localhost")
						|| hostRef.getHostDomain().contains("127.0.0.1");
			}
		};

		
		core.setNext(guard);
		guard.setNext(kdApplication);
		
		router.attachDefault(core);

		return router;

	}
	
	@Override
	public Restlet createOutboundRoot() {
		return new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				new Client(request.getProtocol()).handle(request, response);
			}
		};
	}

}
