package com.kdcloud.ext.rehab.doctor;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.RehabDoctor;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public abstract class RehabDoctorServerResource extends KDServerResource {

	// il dottore in "sessione"
	RehabDoctor rehabDoctor;

	@Override
	public void beforeHandle() {
		super.beforeHandle();
		User u = getUser();
		String username = u.getName();
		
		try {
			ObjectifyService.register(RehabDoctor.class);
		} catch (Exception e) {
		}

		Objectify ofy = ObjectifyService.begin();
		rehabDoctor = ofy.query(RehabDoctor.class).filter("username", username)
				.get();
		if (rehabDoctor == null)
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
	}

}
