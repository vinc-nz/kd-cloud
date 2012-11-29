package com.kdcloud.ext.rehab.paziente;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;

public class LoginRehabUserRestlet extends RehabServerResource {

	public static final String URI = "/rehab/loginrehabuser";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = rehabUser.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			// handle input document
			Element rootEl = doc.getDocumentElement();
			String u = XMLUtils.getTextValue(rootEl, "username");

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			try {
				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

			Map<String, String> map = new LinkedHashMap<String, String>();

			if (!username.equals(u)) {
				map.put("login", "login error");
				d = XMLUtils.createXMLResult("loginrehabuserOutput", map, d);
			} else {
				Element root = d.createElement("loginrehabuserOutput");
				d.appendChild(root);
				root.setAttribute("username", rehabUser.getUsername());
				root.setAttribute("firstname", rehabUser.getFirstName());
				root.setAttribute("lastname", rehabUser.getLastName());
				int n = rehabUser.getRegisteredExercises();
				root.setAttribute("registered_exercises", "" + n);

				if (n > 0) {
					List<CompleteExercise> l = new ArrayList<CompleteExercise>();
					l = ofy.query(CompleteExercise.class)
							.filter("paziente", us).list();

					// for (CompleteExercise es : l) {
					// map.put("esercizio_" + es.getNumber(),
					// "" + es.getName());
					// }

					Element eltName4 = d.createElement("exercises_list");
					for (CompleteExercise es : l) {
						Element eserc = d.createElement("exercise");
						eserc.setAttribute("number", "" + es.getNumber());
						eserc.appendChild(d.createTextNode(es.getName()));
						eltName4.appendChild(eserc);
					}
					root.appendChild(eltName4);
				}

				d.normalizeDocument();
			}

		} catch (Exception e) {
			result = XMLUtils.createXMLError("login error",
					"" + e.getMessage());
		}

		return result;
	}

}
