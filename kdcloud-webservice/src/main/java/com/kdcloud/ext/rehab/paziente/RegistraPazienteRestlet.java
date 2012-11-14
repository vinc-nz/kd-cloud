package com.kdcloud.ext.rehab.paziente;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.rest.resource.KDServerResource;

public class RegistraPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/registrapaziente";

	@Post
	protected DomRepresentation doPost(DomRepresentation d) {

		// User user = getUser();
		// String username = user.getName();
		
		Document doc = null;
		try {
			doc = d.getDocument();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// handle document input
		Element rootEl = doc.getDocumentElement();
		String username = XMLUtils.getTextValue(rootEl, "username");
		String password = XMLUtils.getTextValue(rootEl, "password");
		String nome = XMLUtils.getTextValue(rootEl, "nome");
		String cognome = XMLUtils.getTextValue(rootEl, "cognome");

		String esito = "";
		try {
			ObjectifyService.register(Paziente.class);
		} catch (Exception e) {
		}

		Objectify ofy = ObjectifyService.begin();
		Paziente paz = ofy.query(Paziente.class).filter("username", username)
				.get();
		if (paz != null) {
			esito = "errore";
		} else {

			Paziente paziente = new Paziente(username, nome, cognome);
			ofy.put(paziente);
			esito = "OK";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("esito", esito);
		Document ris = XMLUtils.createXMLResult("registrapazienteOutput", map);
		
		DomRepresentation result = new DomRepresentation(MediaType.TEXT_XML, ris);
		
		return result;

	}

}
