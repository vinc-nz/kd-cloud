package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class NumeroEserciziRestlet extends KDServerResource {

	public static final String URI = "/rehab/numeroesercizi";

	@Post
	protected String doPost(Form form) {

		String ris = "";
		User user = getUser();
		String username = user.getName();
		Paziente paziente = getPazienteByUsername(username);
		//se è lento, evitare la query
		
		String getOrUpdate = form.getFirstValue("getorupdate");
		if (getOrUpdate.equals("update")) {
			paziente.setNumeroEserciziRegistrati(paziente
					.getNumeroEserciziRegistrati() + 1);
			try {
				ObjectifyService.register(Paziente.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Objectify ofy = ObjectifyService.begin();

			ofy.put(paziente);
			ris =  paziente.getNumeroEserciziRegistrati()
					+ "- numero esercizi registrati ";
		} else if (getOrUpdate.equals("get")) {
			
			ris =  paziente.getNumeroEserciziRegistrati()
					+ "";

		}
		return ris;
		


	}
	
	private Paziente getPazienteByUsername(String username) {
		try {
			ObjectifyService.register(Paziente.class);
		} catch (Exception e) {

		}
		Objectify ofy = ObjectifyService.begin();
		Paziente paziente = ofy.get(Paziente.class, username);
		return paziente;
	}

}
