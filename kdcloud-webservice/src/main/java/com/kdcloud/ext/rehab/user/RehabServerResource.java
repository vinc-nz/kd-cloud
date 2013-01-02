package com.kdcloud.ext.rehab.user;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public abstract class RehabServerResource extends KDServerResource {

	// il paziente in "sessione"
	RehabUser rehabUser;

	@Override
	public void beforeHandle() {
		super.beforeHandle();
		// importante
		// paziente = <cerca paziente>
		// if (paziente == null)
		User u = getUser();
		String username = u.getName();
		
		try {
			ObjectifyService.register(RehabUser.class);
		} catch (Exception e) {
		}

		Objectify ofy = ObjectifyService.begin();
		rehabUser = ofy.query(RehabUser.class).filter("username", username)
				.get();
		if (rehabUser == null)
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
	}

}
