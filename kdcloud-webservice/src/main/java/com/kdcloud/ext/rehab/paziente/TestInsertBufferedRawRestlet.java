package com.kdcloud.ext.rehab.paziente;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.kdcloud.ext.rehab.db.BufferedRaw;
import com.kdcloud.ext.rehab.db.EsercizioCompleto;
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class TestInsertBufferedRawRestlet extends RehabServerResource {

	public static final String URI = "/rehab/insertbufferedraw";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			BufferedRaw bufferedRaw = new BufferedRaw();
			String username = paziente.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			// handle document input
			Element rootEl = doc.getDocumentElement();
			bufferedRaw.setElbowknee(Integer.parseInt(rootEl.getAttribute("elbowknee")));			
			bufferedRaw.setData(new Date());
			bufferedRaw.setLenght(Integer.parseInt(rootEl.getAttribute("lenght")));
			
			List<Integer[]> rawdataList = new LinkedList<Integer[]>();
			List<Integer[]> anglesdataList = new LinkedList<Integer[]>();
			Integer[] rawData = new Integer[6];
			Integer[] anglesData = new Integer[4];
			
			NodeList nl = rootEl.getElementsByTagName("raw_data");
			if (nl != null && nl.getLength() > 0) {
				for(int i = 0; i < nl.getLength(); i++){
					Element el = (Element) nl.item(i);
					rawData[0] = Integer.parseInt(el.getAttribute("bx"));
					rawData[1] = Integer.parseInt(el.getAttribute("by"));
					rawData[2] = Integer.parseInt(el.getAttribute("bz"));
					rawData[3] = Integer.parseInt(el.getAttribute("fx"));
					rawData[4] = Integer.parseInt(el.getAttribute("fy"));
					rawData[5] = Integer.parseInt(el.getAttribute("fz"));
					rawdataList.add(rawData);
				}
				
			}
			nl = rootEl.getElementsByTagName("angles_data");
			if (nl != null && nl.getLength() > 0) {
				for(int i = 0; i < nl.getLength(); i++){
					Element el = (Element) nl.item(i);
					anglesData[0] = Integer.parseInt(el.getAttribute("elbowknee"));
					anglesData[1] = Integer.parseInt(el.getAttribute("backline"));
					anglesData[2] = Integer.parseInt(el.getAttribute("foreline"));
					anglesData[3] = Integer.parseInt(el.getAttribute("sideangle"));
					anglesdataList.add(anglesData);
				}
				
			}
			
			bufferedRaw.setRaw(rawdataList);
			bufferedRaw.setAngoli(anglesdataList);

			try {
				ObjectifyService.register(BufferedRaw.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
			bufferedRaw.setPaziente(paz);

			ofy.put(bufferedRaw);

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("buffered_raw_inserito", "OK");
			d = XMLUtils.createXMLResult("insertbufferedrawOutput", map, d);

		} catch (Exception e) {
			try {
				result = new DomRepresentation(MediaType.TEXT_XML);
				d = result.getDocument();
			} catch (IOException e1) {
			}
			d = XMLUtils.createXMLError(d, "errore insert esercizio completo",
					"" + e.getMessage());
		}

		return result;

	}



}
