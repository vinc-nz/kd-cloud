package com.kdcloud.ext.rehab.paziente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class TestDownloadEsercizioCompletoRestlet extends  RehabServerResource{// KDServerResource { //
																	

	public static final String URI = "/rehab/downloadeserciziocompleto";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			// String username = paziente.getUsername();
			User user = getUser();
			String username = user.getName();

			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			int numero = XMLUtils.getIntValue(rootEl, "numero");
			String nome = XMLUtils.getTextValue(rootEl, "nome");

			try {
				ObjectifyService.register(EsercizioCompleto.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);

			List<EsercizioCompleto> l = new ArrayList<EsercizioCompleto>();
			l = ofy.query(EsercizioCompleto.class)
					.filter("numero", numero).list();
			EsercizioCompleto esercizio = null;

			for(EsercizioCompleto es: l){
				if(es.getPaziente().equals(paz) && es.getNome().equals(es.getNome())){
					esercizio = es;
					break;
				}
			}
			
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			if(esercizio != null)
				d = esercizio.toXMLDocument(d);
			else
				result = XMLUtils.createXMLError("errore download esercizio completo", "nessun esercizio trovato");
			


		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore download esercizio", ""
					+ e.getMessage());
		}

		return result;
	}


}
