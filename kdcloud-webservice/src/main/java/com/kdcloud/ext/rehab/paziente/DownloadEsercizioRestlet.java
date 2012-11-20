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
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadEsercizioRestlet extends  RehabServerResource{// KDServerResource { //
																	

	public static final String URI = "/rehab/downloadesercizio";

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
			int esercizio = XMLUtils.getIntValue(rootEl, "esercizio");

			try {
				ObjectifyService.register(EsercizioRiferimento.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);

			List<EsercizioRiferimento> l = new ArrayList<EsercizioRiferimento>();
			l = ofy.query(EsercizioRiferimento.class)
					.filter("numero", esercizio).list();
			// .filter("paziente", paz).order("timestamp").

			Collections.sort(l);
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			Element root = d.createElement("downloadesercizioOutput");
			d.appendChild(root);
			root.setAttribute("numero", "" + esercizio);
			for (EsercizioRiferimento es : l) {
				if (es.getPaziente().equals(paz)) {
					Element eserc = d.createElement("esercizio");
					eserc.setAttribute("timestamp", "" + es.getTimestamp());
					Element rawdata = d.createElement("raw");
					int[] raw = es.getRaw();
					rawdata.setAttribute("bx", "" + raw[0]);
					rawdata.setAttribute("by", "" + raw[1]);
					rawdata.setAttribute("bz", "" + raw[2]);
					rawdata.setAttribute("fx", "" + raw[3]);
					rawdata.setAttribute("fy", "" + raw[4]);
					rawdata.setAttribute("fz", "" + raw[5]);
//					rawdata.appendChild(d.createTextNode(Arrays.toString(es
//							.getRaw())));
					eserc.appendChild(rawdata);
					Element angles = d.createElement("angles");
					int[] angoli = es.getAngoli();
					angles.setAttribute("elbowknee", "" + angoli[0]);
					angles.setAttribute("backline", "" + angoli[1]);
					angles.setAttribute("foreline", "" + angoli[2]);
					angles.setAttribute("sideangle", "" + angoli[3]);
//					angles.appendChild(d.createTextNode(Arrays.toString(es
//							.getAngoli())));

					eserc.appendChild(angles);
					root.appendChild(eserc);
				}
			}

			d.normalizeDocument();


		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore download esercizio", ""
					+ e.getMessage());
		}

		return result;
	}


}
