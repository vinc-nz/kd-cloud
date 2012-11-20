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
import org.w3c.dom.NodeList;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.angles.AngleController;
import com.kdcloud.ext.rehab.angles.CalibrationController;
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadDataPacketProvaRestlet extends RehabServerResource {

	public static final String URI = "/rehab/downloaddatapacket";

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
			int esercizio = XMLUtils.getIntValue(rootEl, "esercizio");

			try {
				ObjectifyService.register(RawDataPacket.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);

			List<RawDataPacket> l = new ArrayList<RawDataPacket>();
			l = ofy.query(RawDataPacket.class).list();

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			Map<String, String> map = new HashMap<String, String>();
			map.put("ciao", "aaa");
			if (l != null) {
				map.put("list", "NOTNULL");
				map.put("listsize", ""+l.size());
				int i = 0;
				for(RawDataPacket r: l){
					map.put("listelement"+i, ""+r.toString());
					i++;
				}
			} else
				map.put("list", "NULL");
			// map.put("listsize", l.size()+"");
			// for(int i = 0; i<l.size(); i++)
			// map.put("rawdatapacket " + i, l.get(i).toString());
			d = XMLUtils.createXMLResult("downloaddatapacketOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore download raw packet test",
					"" + e.getMessage());
		}

		return result;

	}

}
