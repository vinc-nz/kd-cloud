package com.kdcloud.ext.rehab.paziente;

import java.util.Date;
import java.util.HashMap;
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
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class ComputeAnglesRestlet extends RehabServerResource {

	public static final String URI = "/rehab/computeangles";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = rehabUser.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();

			// handle document input
			Element rootEl = doc.getDocumentElement();
			int timestamp = XMLUtils.getIntValue(rootEl, "timestamp");
			int elbowknee = XMLUtils.getIntValue(rootEl, "elbowknee");
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
			
			CalibrationController.F_MIN = rehabUser.getF_MIN();
			CalibrationController.F_MAX = rehabUser.getF_MAX();
			CalibrationController.F_ZERO = rehabUser.getF_ZERO();
			CalibrationController.B_MIN = rehabUser.getB_MIN();
			CalibrationController.B_MAX = rehabUser.getB_MAX();
			CalibrationController.B_ZERO = rehabUser.getB_ZERO();

			// calcolo angoli
			AngleController controller = new AngleController();
			int[] angoli = controller.computeAngles(rawData, elbowknee);


			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			Map<String, String> map = new HashMap<String, String>();
			map.put("timestamp", "" + timestamp);
			map.put("elbowknee", "" + angoli[0]);
			map.put("backline", "" + angoli[1]);
			map.put("foreline", "" + angoli[2]);
			map.put("sideangle", "" + angoli[3]);
			d = XMLUtils.createXMLResult("computeanglesOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("compute angles error", ""
					+ e.getMessage());
		}

		return result;

	}

}
