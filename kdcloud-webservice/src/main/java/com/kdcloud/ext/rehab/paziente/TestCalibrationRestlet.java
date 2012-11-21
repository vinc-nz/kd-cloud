package com.kdcloud.ext.rehab.paziente;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.angles.CalibrationController;
import com.kdcloud.ext.rehab.db.Paziente;

public class TestCalibrationRestlet extends RehabServerResource {

	public static final String URI = "/rehab/calibration";

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
			
			int[] F_MIN = new int[3];
			int[] F_MAX = new int[3];
			int[] F_ZERO = new int[3];
			int[] B_MIN = new int[3];
			int[] B_MAX = new int[3];
			int[] B_ZERO = new int[3];
			
			NodeList nl = rootEl.getElementsByTagName("calibration_F_MIN");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				F_MIN[0] = XMLUtils.getIntValue(el, "x");
				F_MIN[1] = XMLUtils.getIntValue(el, "y");
				F_MIN[2] = XMLUtils.getIntValue(el, "z");
			}
			nl = rootEl.getElementsByTagName("calibration_F_MAX");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				F_MAX[0] = XMLUtils.getIntValue(el, "x");
				F_MAX[1] = XMLUtils.getIntValue(el, "y");
				F_MAX[2] = XMLUtils.getIntValue(el, "z");
			}
			nl = rootEl.getElementsByTagName("calibration_F_ZERO");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				F_ZERO[0] = XMLUtils.getIntValue(el, "x");
				F_ZERO[1] = XMLUtils.getIntValue(el, "y");
				F_ZERO[2] = XMLUtils.getIntValue(el, "z");
			}
			nl = rootEl.getElementsByTagName("calibration_B_MIN");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				B_MIN[0] = XMLUtils.getIntValue(el, "x");
				B_MIN[1] = XMLUtils.getIntValue(el, "y");
				B_MIN[2] = XMLUtils.getIntValue(el, "z");
			}
			nl = rootEl.getElementsByTagName("calibration_B_MAX");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				B_MAX[0] = XMLUtils.getIntValue(el, "x");
				B_MAX[1] = XMLUtils.getIntValue(el, "y");
				B_MAX[2] = XMLUtils.getIntValue(el, "z");
			}
			nl = rootEl.getElementsByTagName("calibration_B_ZERO");
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				// {bx, by, bz, fx, fy, fz}
				B_ZERO[0] = XMLUtils.getIntValue(el, "x");
				B_ZERO[1] = XMLUtils.getIntValue(el, "y");
				B_ZERO[2] = XMLUtils.getIntValue(el, "z");
			}

			
			CalibrationController.F_MIN = F_MIN;
			CalibrationController.F_MAX = F_MAX;
			CalibrationController.F_ZERO = F_ZERO;
			CalibrationController.B_MIN = B_MIN;
			CalibrationController.B_MAX = B_MAX;
			CalibrationController.B_ZERO = B_ZERO;
			
			paziente.setF_MIN(F_MIN);
			paziente.setF_MAX(F_MAX);
			paziente.setF_ZERO(F_ZERO);
			paziente.setB_MIN(B_MIN);
			paziente.setB_MAX(B_MAX);
			paziente.setB_ZERO(B_ZERO);

			
			// salvataggio nel DB
			try {
				ObjectifyService.register(Paziente.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			ofy.put(paziente);

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			Map<String, String> map = new HashMap<String, String>();
			map.put("calibrazione", "ok");
			d = XMLUtils.createXMLResult("calibrazioneOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("errore calibrazione", ""
					+ e.getMessage());
		}

		return result;

	}

}
