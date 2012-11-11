package com.kdcloud.ext.rehab.paziente;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;


public class DownloadEsercizioRestlet extends KDServerResource {

	public static final String URI = "/rehab/downloadesercizio";

	@Post
	protected Document doPost(Document doc) {

		User user = getUser();
		String username = user.getName();

		Element rootEl = doc.getDocumentElement();
		int esercizio = XMLUtils.getIntValue(rootEl, "esercizio");

		
		Document ris = getEsercizioByPazienteAndNumero(username, esercizio);

		return ris;

	}

	private Document getEsercizioByPazienteAndNumero(String username,
			int esercizio) {

		Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);

		try {
			ObjectifyService.register(EsercizioRiferimento.class);
		} catch (Exception e) {

		}

		Objectify ofy = ObjectifyService.begin();
		List<EsercizioRiferimento> list = ofy.query(EsercizioRiferimento.class)
				.order("timestamp").filter("numero", esercizio).list();

		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("downloadesercizioOutput");
			root.setAttribute("numero", "" + esercizio);
			doc.appendChild(root);

			for (EsercizioRiferimento e : list) {
				int[] raw = e.getRaw();
				int[] angles = e.getAngoli();
				Element child = doc.createElement("esercizio");

				child.setAttribute("timestamp", "" + e.getTimestamp());
				child.setAttribute("raw_bx", "" + raw[0]);
				child.setAttribute("raw_by", "" + raw[1]);
				child.setAttribute("raw_bz", "" + raw[2]);
				child.setAttribute("raw_fx", "" + raw[3]);
				child.setAttribute("raw_fy", "" + raw[4]);
				child.setAttribute("raw_fz", "" + raw[5]);
				child.setAttribute("elbowknee", "" + angles[0]);
				child.setAttribute("backline", "" + angles[1]);
				child.setAttribute("foreline", "" + angles[2]);
				child.setAttribute("sideangle", "" + angles[3]);
				

				root.appendChild(child);
				return doc;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
