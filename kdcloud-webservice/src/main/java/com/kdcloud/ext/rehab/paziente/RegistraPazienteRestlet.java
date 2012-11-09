package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.rest.resource.KDServerResource;

public class RegistraPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/registrapaziente";

	@Post
	protected String doPost(Form form) {

		// User user = getUser();
		// String username = user.getName();

		String username = form.getFirstValue("username");
		String password = form.getFirstValue("password");
		String nome = form.getFirstValue("nome");
		String cognome = form.getFirstValue("cognome");

		try {
			ObjectifyService.register(Paziente.class);
		} catch (Exception e) {
		}

		Objectify ofy = ObjectifyService.begin();
		Paziente paz = ofy.query(Paziente.class).filter("username", username)
				.get();
		if (paz != null) {
			return "errore: username già in uso";
		}

		Paziente paziente = new Paziente(username, nome, cognome);
		ofy.put(paziente);
		return "registrazione nuovo utente effettuata correttamente";

	}

}
