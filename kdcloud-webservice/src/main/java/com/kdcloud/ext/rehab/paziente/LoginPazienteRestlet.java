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
import com.kdcloud.ext.rehab.db.EsercizioCompleto;
import com.kdcloud.ext.rehab.db.Paziente;

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
			
			try {
				ObjectifyService.register(EsercizioCompleto.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);

			
			
			
			Map<String, String> map = new LinkedHashMap<String, String>();

			if (!username.equals(u))
				map.put("login", "errore login");
			else {				
				
				List<EsercizioCompleto> l = new ArrayList<EsercizioCompleto>();
				l = ofy.query(EsercizioCompleto.class).filter("paziente", paz).list();

				for(EsercizioCompleto es: l){
					map.put("esercizio_"+es.getNumero(),
							"" + es.getNome());
				}
				Element root = d.createElement("loginpazienteOutput");
				d.appendChild(root);
				root.setAttribute("username", paziente.getUsername());
				root.setAttribute("nome", paziente.getNome());
				root.setAttribute("cognome", paziente.getCognome());
				root.setAttribute("numero_esercizi","" + paziente.getNumeroEserciziRegistrati());

				Element eltName4 = d.createElement("esercizi");
				for(EsercizioCompleto es: l){
					Element eserc = d.createElement("esercizio");
					eserc.setAttribute("numero", ""+es.getNumero());
					eserc.appendChild(d.createTextNode(es.getNome()));
					eltName4.appendChild(eserc);
				}
				root.appendChild(eltName4);

				d.normalizeDocument();
			}
			d = XMLUtils.createXMLResult("loginpazienteOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore login",
					"" + e.getMessage());
		}

		return result;
	}

}
