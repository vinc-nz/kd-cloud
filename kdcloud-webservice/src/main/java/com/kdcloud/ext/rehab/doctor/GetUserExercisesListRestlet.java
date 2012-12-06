package com.kdcloud.ext.rehab.doctor;

import java.util.ArrayList;
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
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabDoctor;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;

public class GetUserExercisesListRestlet extends RehabDoctorServerResource {

	public static final String URI = "/rehabdoctor/getuserexerciseslist";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			// handle input document
			Element rootEl = doc.getDocumentElement();
			String u = XMLUtils.getTextValue(rootEl, "username");

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			try {
				ObjectifyService.register(CompleteExercise.class);
				ObjectifyService.register(RehabUser.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, u);
			Key<RehabDoctor> doctor = new Key<RehabDoctor>(RehabDoctor.class,
					rehabDoctor.getUsername());

			RehabUser user = ofy.query(RehabUser.class).filter("username", u)
					.get();

			if (user != null && user.getDoctor().equals(doctor)) {

				Map<String, String> map = new LinkedHashMap<String, String>();

				Element root = d.createElement("getuserexerciseslistOutput");
				d.appendChild(root);
				List<CompleteExercise> l = new ArrayList<CompleteExercise>();
				l = ofy.query(CompleteExercise.class).filter("rehabuser", us).order("number")
						.list();

				Element eltName4 = d.createElement("exercises_list");
				for (CompleteExercise es : l) {
					Element eserc = d.createElement("exercise");
					eserc.setAttribute("number", "" + es.getNumber());
					eserc.appendChild(d.createTextNode(es.getName()));
					eltName4.appendChild(eserc);
				}
				root.appendChild(eltName4);

				d.normalizeDocument();
			}else{
				result = XMLUtils
						.createXMLError("get user exercises list error", "user error");
			}

		} catch (Exception e) {
			result = XMLUtils
					.createXMLError("get user exercises list error", "" + e.getMessage());
		}

		return result;
	}

}
