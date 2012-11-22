package com.kdcloud.ext.rehab.paziente;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.kdcloud.ext.rehab.db.DualModeSession;
import com.kdcloud.ext.rehab.db.EsercizioCompleto;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertDualModeSessionRestlet extends RehabServerResource {

	public static final String URI = "/rehab/insertdualmodesession";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = paziente.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();

			// handle document input
			Element rootEl = doc.getDocumentElement();
			String nome = XMLUtils.getTextValue(rootEl, "nome");
			int numeroEsercizio = XMLUtils.getIntValue(rootEl, "numero");

			Date data = new Date();

			try {
				ObjectifyService.register(DualModeSession.class);
				ObjectifyService.register(EsercizioCompleto.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			DualModeSession dms;
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
			

//			List<EsercizioCompleto> l = new ArrayList<EsercizioCompleto>();
//			l = ofy.query(EsercizioCompleto.class)
//					.filter("numero", numeroEsercizio).list();
//			EsercizioCompleto esercizio = null;
//
//			for(EsercizioCompleto es: l){
//				if(es.getPaziente().equals(paz) && es.getNome().equals(es.getNome())){
//					esercizio = es;
//					break;
//				}
//			}
			
			EsercizioCompleto esercizio = ofy.query(EsercizioCompleto.class)
					.filter("numero", numeroEsercizio).filter("paziente", paz)
					.filter("nome", nome).get();
			
			Key<EsercizioCompleto> es = new Key<EsercizioCompleto>(EsercizioCompleto.class, esercizio.getId());

			dms = new DualModeSession();
			dms.setDataInizio(data);
			dms.setPaziente(paz);
			dms.setEsercizio(es);
			ofy.put(dms);

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			Map<String, String> map = new HashMap<String, String>();
			map.put("dualmode", "saved");
			d = XMLUtils.createXMLResult("insertdualmodesessionOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore insert dual mode session",
					"" + e.getMessage());
		}

		return result;

	}

}
