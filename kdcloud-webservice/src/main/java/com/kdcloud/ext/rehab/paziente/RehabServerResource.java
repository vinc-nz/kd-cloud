package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public abstract class RehabServerResource extends KDServerResource {

	// il paziente in "sessione"
	// Paziente paziente
	Paziente paziente;

	@Override
	public void beforeHandle() {
		super.beforeHandle();
		// importante
		// paziente = <cerca paziente>
		// if (paziente == null)
		User user = getUser();
		String username = user.getName();
		try {
			ObjectifyService.register(Paziente.class);
		} catch (Exception e) {
		}

		Objectify ofy = ObjectifyService.begin();
		paziente = ofy.query(Paziente.class).filter("username", username)
				.get();
		if (paziente == null)
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
	}

}
