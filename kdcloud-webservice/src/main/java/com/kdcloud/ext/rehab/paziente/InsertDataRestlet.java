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
import com.kdcloud.ext.rehab.angles.AngleController;
import com.kdcloud.ext.rehab.angles.CalibrationController;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertDataRestlet extends KDServerResource {

	public static final String URI = "/rehab/insertdata";

	@Post
	protected Document doPost(Document doc) {

		User user = getUser();
		String username = user.getName();
		
		//handle document input 
		Element rootEl = doc.getDocumentElement();
		int timestamp = XMLUtils.getIntValue(rootEl,"timestamp");
		int elbowknee = XMLUtils.getIntValue(rootEl,"elbowknee");
		int storeRawData = XMLUtils.getIntValue(rootEl,"storeraw");	
		int[] rawData = new int[6];
		NodeList nl = rootEl.getElementsByTagName("raw");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			rawData[0] = XMLUtils.getIntValue(el,"bx");
			rawData[1] = XMLUtils.getIntValue(el,"by");
			rawData[2] = XMLUtils.getIntValue(el,"bz");			
			rawData[3] = XMLUtils.getIntValue(el,"fx");			
			rawData[4] = XMLUtils.getIntValue(el,"fy");			
			rawData[5] = XMLUtils.getIntValue(el,"fz");
		}
		int[] F_MIN = new int[3];
		int[] F_MAX = new int[3];
		int[] F_ZERO = new int[3];
		int[] B_MIN = new int[3];
		int[] B_MAX = new int[3];
		int[] B_ZERO = new int[3];
		nl = rootEl.getElementsByTagName("calibration_F_MIN");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			F_MIN[0] = XMLUtils.getIntValue(el,"x");
			F_MIN[1] = XMLUtils.getIntValue(el,"y");
			F_MIN[2] = XMLUtils.getIntValue(el,"z");	
		}
		nl = rootEl.getElementsByTagName("calibration_F_MAX");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			F_MAX[0] = XMLUtils.getIntValue(el,"x");
			F_MAX[1] = XMLUtils.getIntValue(el,"y");
			F_MAX[2] = XMLUtils.getIntValue(el,"z");	
		}
		nl = rootEl.getElementsByTagName("calibration_F_ZERO");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			F_ZERO[0] = XMLUtils.getIntValue(el,"x");
			F_ZERO[1] = XMLUtils.getIntValue(el,"y");
			F_ZERO[2] = XMLUtils.getIntValue(el,"z");	
		}
		nl = rootEl.getElementsByTagName("calibration_B_MIN");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			B_MIN[0] = XMLUtils.getIntValue(el,"x");
			B_MIN[1] = XMLUtils.getIntValue(el,"y");
			B_MIN[2] = XMLUtils.getIntValue(el,"z");	
		}
		nl = rootEl.getElementsByTagName("calibration_B_MAX");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			B_MAX[0] = XMLUtils.getIntValue(el,"x");
			B_MAX[1] = XMLUtils.getIntValue(el,"y");
			B_MAX[2] = XMLUtils.getIntValue(el,"z");	
		}
		nl = rootEl.getElementsByTagName("calibration_B_ZERO");
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			//{bx, by, bz, fx, fy, fz}
			B_ZERO[0] = XMLUtils.getIntValue(el,"x");
			B_ZERO[1] = XMLUtils.getIntValue(el,"y");
			B_ZERO[2] = XMLUtils.getIntValue(el,"z");	
		}
		

		
		Date data = new Date();
		CalibrationController.F_MIN = F_MIN;
		CalibrationController.F_MAX = F_MAX;
		CalibrationController.F_ZERO = F_ZERO;
		CalibrationController.B_MIN = B_MIN;
		CalibrationController.B_MAX = B_MAX;
		CalibrationController.B_ZERO = B_ZERO;

		// calcolo angoli
		AngleController controller = new AngleController();
		int[] angoli = controller.computeAngles(rawData, elbowknee);

		// salvataggio nel DB
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
		map.put("timestamp", ""+timestamp);
		map.put("elbowknee", ""+angoli[0]);
		map.put("backline", ""+angoli[1]);
		map.put("foreline", ""+angoli[2]);
		map.put("sideangle", ""+angoli[3]);
		Document ris = XMLUtils.createXMLResult("insertdataOutput", map);


		return ris;

	}





	


}
