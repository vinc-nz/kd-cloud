package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadEsercizioRestlet extends RehabServerResource { //KDServerResource{ //

	public static final String URI = "/rehab/downloadesercizio";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		
		DomRepresentation result = null;
		Document d = null;
		try {
			String username = paziente.getUsername();

			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			int esercizio = XMLUtils.getIntValue(rootEl, "esercizio");
			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			d = getEsercizioByPazienteAndNumero(username, esercizio, d);

		} catch (Exception e) {
			// ritorna l'xml di errore col messaggio
			// result = errore..
			result = XMLUtils.createXMLError("errore download esercizio", ""
					+ e.getMessage());
		}

		return result;
	}

	private Document getEsercizioByPazienteAndNumero(String username,
			int esercizio, Document doc) throws Exception {

//		Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
//
//		try {
//			ObjectifyService.register(EsercizioRiferimento.class);
//		} catch (Exception e) {
//
//		}
//
//		Objectify ofy = ObjectifyService.begin();
//		List<EsercizioRiferimento> list = ofy.query(EsercizioRiferimento.class)
//				.order("timestamp").filter("paziente", paz)
//				.filter("numero", esercizio).list();
		
		Element root = doc.createElement("downloadesercizioOutput");
		root.setAttribute("numero", "" + esercizio);
		root.setAttribute("paziente", "" + username);
		doc.appendChild(root);

//		if (list != null & !list.isEmpty()) {
//
//			
//
//			for (EsercizioRiferimento e : list) {
//				int[] raw = e.getRaw();
//				int[] angles = e.getAngoli();
//				Element child = doc.createElement("esercizio");
//
//				child.setAttribute("timestamp", "" + e.getTimestamp());
//				child.setAttribute("raw_bx", "" + raw[0]);
//				child.setAttribute("raw_by", "" + raw[1]);
//				child.setAttribute("raw_bz", "" + raw[2]);
//				child.setAttribute("raw_fx", "" + raw[3]);
//				child.setAttribute("raw_fy", "" + raw[4]);
//				child.setAttribute("raw_fz", "" + raw[5]);
//				child.setAttribute("elbowknee", "" + angles[0]);
//				child.setAttribute("backline", "" + angles[1]);
//				child.setAttribute("foreline", "" + angles[2]);
//				child.setAttribute("sideangle", "" + angles[3]);
//
//				root.appendChild(child);
//
//			}
//
//		}
		return doc;

	}

}
