package com.kdcloud.ext.rehab.paziente;

import java.util.Date;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.DualModeSession;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertDualModeSessionRestlet extends KDServerResource {

	public static final String URI = "/rehab/insertdualmodesession";

	@Post
	protected String doPost(Form form) {

		User user = getUser();
		String username = user.getName();
		
		String num = form.getFirstValue("numeroEsercizio");
		int numeroEsercizio = Integer.parseInt(num);
		
		Date data = new Date();

		try {
			ObjectifyService.register(DualModeSession.class);
		} catch (Exception e) {
		}
		Objectify ofy = ObjectifyService.begin();
		DualModeSession dms;
		Key<Paziente> paz = new Key<Paziente>(Paziente.class,
				username);

		dms = new DualModeSession(paz, data, numeroEsercizio);
		ofy.put(dms);
		
		return " dual mode session started ";
		
		

		

	}

}
