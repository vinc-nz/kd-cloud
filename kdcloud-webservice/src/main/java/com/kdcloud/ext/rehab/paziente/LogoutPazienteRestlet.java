package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.rest.resource.KDServerResource;

public class LogoutPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/logoutpaziente";

	@Post
	protected String doPost(Form form) {

		// User user = getUser();
		// String username = user.getName();

		//TODO Google Logout 
		
		String username = form.getFirstValue("username");
		return "logout effettuato correttamente " + username;
		
	}

}
