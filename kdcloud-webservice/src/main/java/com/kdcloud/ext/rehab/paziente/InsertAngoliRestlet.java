package com.kdcloud.ext.rehab.paziente;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertAngoliRestlet extends KDServerResource {

	public static final String URI = "/rehab/insertangoli";

	@Post
	protected Document doPost(Document doc) {

		User user = getUser();
		String username = user.getName();

		// handle document input
		Element rootEl = doc.getDocumentElement();
		int timestamp = XMLUtils.getIntValue(rootEl, "timestamp");
		int elbowknee = XMLUtils.getIntValue(rootEl, "elbowknee");
		int storeRawData = XMLUtils.getIntValue(rootEl, "storeraw");
		int[] rawData = new int[6];
		NodeList nl = rootEl.getElementsByTagName("raw");
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			// {bx, by, bz, fx, fy, fz}
			rawData[0] = XMLUtils.getIntValue(el, "bx");
			rawData[1] = XMLUtils.getIntValue(el, "by");
			rawData[2] = XMLUtils.getIntValue(el, "bz");
			rawData[3] = XMLUtils.getIntValue(el, "fx");
			rawData[4] = XMLUtils.getIntValue(el, "fy");
			rawData[5] = XMLUtils.getIntValue(el, "fz");
		}
		int[] angoli = new int[4];
		nl = rootEl.getElementsByTagName("angles");
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			angoli[0] = XMLUtils.getIntValue(el, "elbowknee");
			angoli[1] = XMLUtils.getIntValue(el, "backline");
			angoli[2] = XMLUtils.getIntValue(el, "foreline");
			angoli[3] = XMLUtils.getIntValue(el, "sideangle");
		}

		
		Date data = new Date();

		try {
			ObjectifyService.register(RawDataPacket.class);
		} catch (Exception e) {
		}
		Objectify ofy = ObjectifyService.begin();
		RawDataPacket r;
		Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
		if (storeRawData == 0)// non salvare i raw data
			r = new RawDataPacket(timestamp, paz, data, null, angoli, elbowknee);
		else
			r = new RawDataPacket(timestamp, paz, data, rawData, angoli, elbowknee);
		ofy.put(r);

		Map<String, String> map = new HashMap<String, String>();
		map.put("timestamp_pubblicato", ""+timestamp);
		Document ris = XMLUtils.createXMLResult("insertangoliOutput", map);
		return ris;

	}

}
