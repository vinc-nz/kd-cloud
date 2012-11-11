package com.kdcloud.ext.rehab.paziente;

import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class NumeroEserciziRestlet extends KDServerResource {

	public static final String URI = "/rehab/numeroesercizi";

	@Post
	protected Document doPost(Document doc) {

		String n = "";
		User user = getUser();
		String username = user.getName();
		Paziente paziente = getPazienteByUsername(username);
		//se è lento, evitare la query
		
		
		Element rootEl = doc.getDocumentElement();
		String getOrUpdate = XMLUtils.getTextValue(rootEl, "getorupdate");
		
		
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
			n =  paziente.getNumeroEserciziRegistrati()
					+ "";
		} else if (getOrUpdate.equals("get")) {
			
			n =  paziente.getNumeroEserciziRegistrati()
					+ "";

		}
		
		
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("esercizi", n);
		Document ris = XMLUtils.createXMLResult("numeroeserciziOutput", map);
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
