package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.rest.resource.KDServerResource;

public class LoginPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/loginpaziente";

	@Post
	protected String doPost(Form form) {

		// User user = getUser();
		// String username = user.getName();

		//TODO Google Login 
		
		String username = form.getFirstValue("username");
		return "login effettuato correttamente " + username;
		
	}

}
