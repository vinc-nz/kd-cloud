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

import java.util.Set;

import org.reflections.Reflections;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;

import com.kdcloud.lib.rest.api.GroupResource;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.rest.resource.IndexServerResource;
import com.kdcloud.server.rest.resource.KDServerResource;
import com.kdcloud.server.rest.resource.UserIndexServerResource;

public class KDApplication extends Application {
	
	public static final ChallengeResponse defaultChallenge = 
			new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "admin", "admin");
	
	private Restlet outboundRoot;
	
	
	public KDApplication(Context context, Restlet outboundRoot) {
		super(context);
		this.outboundRoot = outboundRoot;
	}

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());
		
		//automatically map resources with uri
		Reflections reflections = new Reflections(
				"com.kdcloud.server.rest.resource");

		Set<Class<? extends KDServerResource>> allClasses = reflections
				.getSubTypesOf(KDServerResource.class);

		for (Class<? extends KDServerResource> clazz : allClasses) {
			try {
				getLogger().fine("found resource " + clazz.getSimpleName());
				String uri = clazz.getField("URI").get(null).toString();
				router.attach(uri, clazz);
				getLogger().fine("mapped uri " + uri);
			} catch (Exception e) {
				getLogger().fine("could not map any uri to the class");
			}
		}
		
		//manually map indexes
		router.attach("/engine/workflow", IndexServerResource.class);
		router.attach("/modality", IndexServerResource.class);
		router.attach("/engine/plugin", IndexServerResource.class);
		router.attach("/view", IndexServerResource.class);
		router.attach("/group", IndexServerResource.class);
		
		router.attach(GroupResource.URI + "/" + Group.PROPERTY_CONTRIBUTORS, UserIndexServerResource.class);
		router.attach(GroupResource.URI + "/" + Group.PROPERTY_ENROLLED, UserIndexServerResource.class);
		router.attach(GroupResource.URI + "/" + Group.PROPERTY_MEMBERS, UserIndexServerResource.class);
		
		//redirects
//		for (final Entry<String, String> e : Redirects.getRedirects().entrySet()) {
//			router.attach(e.getKey(), new Restlet() {
//				@Override
//				public void handle(Request request, Response response) {
//					String target = e.getValue();
//					String query = request.getResourceRef().getQuery();
//					if (query != null)
//						target = target + "?" + query;
//					response.redirectPermanent(target);
//					response.commit();
//				}
//			});
//		}

		return router;
	}
	
	@Override
	public Restlet createOutboundRoot() {
		return outboundRoot;
	}

}
