package com.kdcloud.ext.rehab.paziente;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.rest.resource.KDServerResource;

public class RegistraPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/registrapaziente";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		try {
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");
			String nome = XMLUtils.getTextValue(rootEl, "nome");
			String cognome = XMLUtils.getTextValue(rootEl, "cognome");

			// output
			DomRepresentation representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.

			String esito = "";
			try {
				ObjectifyService.register(Paziente.class);
			} catch (Exception e) {
			}

			Objectify ofy = ObjectifyService.begin();
			Paziente paz = ofy.query(Paziente.class)
					.filter("username", username).get();
			if (paz != null) {
				esito = "errore: paziente già registrato";
			} else {

				Paziente paziente = new Paziente(username, nome, cognome);
				ofy.put(paziente);
				esito = "paziente inserito correttamente " + paziente.getUsername();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("esito", esito);
			Document d = representation.getDocument();
			d = XMLUtils.createXMLResult("registrapazienteOutput", map, d);

			// Returns the XML representation of this document.
			return representation;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

//	@Get("xml")
//	public Representation provaGet() {
//		// Generate the right representation according to its media type.
//		try {
//			DomRepresentation representation = new DomRepresentation(
//					MediaType.TEXT_XML);
//
//			// Generate a DOM document representing the list of
//			// items.
//			Document d = representation.getDocument();
//			Element r = d.createElement("items");
//			d.appendChild(r);
//
//			Element eltName = d.createElement("name");
//			eltName.appendChild(d.createTextNode("Fabrix"));
//			r.appendChild(eltName);
//
//			d.normalizeDocument();
//
//			// Returns the XML representation of this document.
//			return representation;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}



}
