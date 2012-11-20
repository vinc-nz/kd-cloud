package com.kdcloud.ext.rehab.paziente;

import java.util.LinkedHashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoginPazienteRestlet extends RehabServerResource {

	public static final String URI = "/rehab/loginpaziente";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = paziente.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			// handle input document
			Element rootEl = doc.getDocumentElement();
			String u = XMLUtils.getTextValue(rootEl, "username");

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			Map<String, String> map = new LinkedHashMap<String, String>();

			if (!username.equals(u))
				map.put("login", "errore login");
			else {
				map.put("username", paziente.getUsername());
				map.put("nome", paziente.getNome());
				map.put("cognome", paziente.getCognome());
				map.put("numero_esercizi",
						"" + paziente.getNumeroEserciziRegistrati());
			}
			d = XMLUtils.createXMLResult("loginpazienteOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore login",
					"" + e.getMessage());
		}

		return result;
	}

}
