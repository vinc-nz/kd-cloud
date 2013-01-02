package com.kdcloud.ext.rehab.user;

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
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertCompleteExerciseRestlet extends RehabServerResource {

	public static final String URI = "/rehab/insertcompleteexercise";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = rehabUser.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();

			CompleteExercise exercise = new CompleteExercise();

			// handle document input
			Element rootEl = doc.getDocumentElement();
			exercise.setName(rootEl.getAttribute("name"));
			exercise
					.setNumber(Integer.parseInt(rootEl.getAttribute("number")));
			exercise.setElbowknee(Integer.parseInt(rootEl
					.getAttribute("elbowknee")));
			exercise.setInsertDate(new Date());
			exercise.setLength(Integer.parseInt(rootEl.getAttribute("length")));

			List<Integer[]> rawdataList = new LinkedList<Integer[]>();
			List<Integer[]> anglesdataList = new LinkedList<Integer[]>();
			Integer[] rawData = new Integer[6];
			Integer[] anglesData = new Integer[4];

			NodeList nl = rootEl.getElementsByTagName("raw_data");
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Element el = (Element) nl.item(i);
					rawData = new Integer[6];
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
				for (int i = 0; i < nl.getLength(); i++) {
					Element el = (Element) nl.item(i);
					anglesData = new Integer[4];
					anglesData[0] = Integer.parseInt(el
							.getAttribute("elbowknee"));
					anglesData[1] = Integer.parseInt(el
							.getAttribute("backline"));
					anglesData[2] = Integer.parseInt(el
							.getAttribute("foreline"));
					anglesData[3] = Integer.parseInt(el
							.getAttribute("sideangle"));
					anglesdataList.add(anglesData);
				}

			}

			exercise.setRaw(rawdataList);
			exercise.setAngles(anglesdataList);

			rehabUser.setRegisteredExercises(rehabUser
					.getRegisteredExercises() + 1);

			try {
				ObjectifyService.register(CompleteExercise.class);
				ObjectifyService.register(RehabUser.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);
			exercise.setRehabUser(us);

			ofy.put(exercise);
			ofy.put(rehabUser);

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			Map<String, String> map = new HashMap<String, String>();
			map.put("exercise_saved", "OK");
			d = XMLUtils.createXMLResult("insertcompleteexerciseOutput", map,
					d);

		} catch (Exception e) {
			try {
				result = new DomRepresentation(MediaType.TEXT_XML);
				d = result.getDocument();
			} catch (IOException e1) {
			}
			d = XMLUtils.createXMLError(d, "insert complete exercise error",
					"" + e.getMessage());
		}

		return result;

	}

}
