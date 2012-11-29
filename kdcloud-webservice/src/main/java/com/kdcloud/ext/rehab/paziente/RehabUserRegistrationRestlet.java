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
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.rest.resource.KDServerResource;

public class RehabUserRegistrationRestlet extends KDServerResource {

	public static final String URI = "/rehab/rehabuserregistration";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation representation = null;
		try {
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");
			String firstName = XMLUtils.getTextValue(rootEl, "firstname");
			String lastName = XMLUtils.getTextValue(rootEl, "lastname");

			// output
			representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.

			String res = "";
			try {
				ObjectifyService.register(RehabUser.class);
			} catch (Exception e) {
			}

			Objectify ofy = ObjectifyService.begin();
			RehabUser us = ofy.query(RehabUser.class)
					.filter("username", username).get();
			if (us != null) {
				res = "user already registered";
			} else {

				RehabUser paziente = new RehabUser(username, firstName, lastName);
				ofy.put(paziente);
				res = "OK" + paziente.getUsername();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("result", res);
			Document d = representation.getDocument();
			d = XMLUtils.createXMLResult("rehabuserregistrationOutput", map, d);

			
		} catch (IOException e) {
			representation = XMLUtils.createXMLError("login error",
					"" + e.getMessage());
		}

		// Returns the XML representation of this document.
		return representation;
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
