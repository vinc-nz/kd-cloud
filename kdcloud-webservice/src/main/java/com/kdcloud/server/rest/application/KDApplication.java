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

import java.util.Set;

import org.reflections.Reflections;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;

import com.kdcloud.ext.rehab.doctor.AddUserSchedulingRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserBufferedDataByIntervalRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserBufferedDataRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserCompleteExerciseRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserDailyStatisticsRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserDualModeSessionsRestlet;
import com.kdcloud.ext.rehab.doctor.DownloadUserRehabDaysRestlet;
import com.kdcloud.ext.rehab.doctor.GetUserExercisesListRestlet;
import com.kdcloud.ext.rehab.doctor.GetUserSchedulingRestlet;
import com.kdcloud.ext.rehab.doctor.LoginRehabDoctorRestlet;
import com.kdcloud.ext.rehab.doctor.RehabDoctorRegistrationRestlet;
import com.kdcloud.ext.rehab.doctor.RehabTestGetRestlet;
import com.kdcloud.ext.rehab.doctor.RehabUserRegistrationRestlet;
import com.kdcloud.ext.rehab.user.CalibrationRestlet;
import com.kdcloud.ext.rehab.user.ComputeAnglesRestlet;
import com.kdcloud.ext.rehab.user.DownloadCompleteExerciseRestlet;
import com.kdcloud.ext.rehab.user.GetSchedulingRestlet;
import com.kdcloud.ext.rehab.user.InsertBufferedDataRestlet;
import com.kdcloud.ext.rehab.user.InsertCompleteExerciseRestlet;
import com.kdcloud.ext.rehab.user.InsertDualModeSessionRestlet;
import com.kdcloud.ext.rehab.user.LoginRehabUserRestlet;
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

		//rehab tutor user restlet
		router.attach(LoginRehabUserRestlet.URI, LoginRehabUserRestlet.class);
		router.attach(DownloadCompleteExerciseRestlet.URI, DownloadCompleteExerciseRestlet.class);
		router.attach(CalibrationRestlet.URI, CalibrationRestlet.class);
		router.attach(ComputeAnglesRestlet.URI, ComputeAnglesRestlet.class);
		router.attach(InsertDualModeSessionRestlet.URI, InsertDualModeSessionRestlet.class);
		router.attach(InsertBufferedDataRestlet.URI, InsertBufferedDataRestlet.class);
		router.attach(InsertCompleteExerciseRestlet.URI, InsertCompleteExerciseRestlet.class);
		router.attach(GetSchedulingRestlet.URI, GetSchedulingRestlet.class);
		
		
		
		//rehab tutor doctor restlet
		router.attach(RehabUserRegistrationRestlet.URI, RehabUserRegistrationRestlet.class);
		router.attach(RehabDoctorRegistrationRestlet.URI, RehabDoctorRegistrationRestlet.class);
		router.attach(LoginRehabDoctorRestlet.URI, LoginRehabDoctorRestlet.class);
		router.attach(GetUserExercisesListRestlet.URI, GetUserExercisesListRestlet.class);
		router.attach(DownloadUserCompleteExerciseRestlet.URI, DownloadUserCompleteExerciseRestlet.class);
		router.attach(DownloadUserDualModeSessionsRestlet.URI, DownloadUserDualModeSessionsRestlet.class);
		router.attach(DownloadUserBufferedDataRestlet.URI, DownloadUserBufferedDataRestlet.class);
		router.attach(DownloadUserBufferedDataByIntervalRestlet.URI, DownloadUserBufferedDataByIntervalRestlet.class);
		router.attach(DownloadUserRehabDaysRestlet.URI, DownloadUserRehabDaysRestlet.class);
		router.attach(DownloadUserDailyStatisticsRestlet.URI, DownloadUserDailyStatisticsRestlet.class);
		router.attach(GetUserSchedulingRestlet.URI, GetUserSchedulingRestlet.class);
		router.attach(AddUserSchedulingRestlet.URI, AddUserSchedulingRestlet.class);
		
		router.attach(RehabTestGetRestlet.URI, RehabTestGetRestlet.class);
		
		return router;
	}
	
	@Override
	public Restlet createOutboundRoot() {
		return outboundRoot;
	}

}
