package com.kdcloud.ext.rehab.paziente;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class NumeroEserciziRestlet extends RehabServerResource {

	public static final String URI = "/rehab/numeroesercizi";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = paziente.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();

			String n = "";

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
				n = paziente.getNumeroEserciziRegistrati() + "";
			} else if (getOrUpdate.equals("get")) {

				n = paziente.getNumeroEserciziRegistrati() + "";

			}

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("esercizi", n);
			d = XMLUtils.createXMLResult("numeroeserciziOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore numero esercizio",
					"" + e.getMessage());
		}

		return result;

	}

}
