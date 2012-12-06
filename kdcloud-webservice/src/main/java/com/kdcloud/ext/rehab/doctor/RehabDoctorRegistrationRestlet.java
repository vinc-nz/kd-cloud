package com.kdcloud.ext.rehab.doctor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.RehabDoctor;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.rest.resource.KDServerResource;

public class RehabDoctorRegistrationRestlet extends KDServerResource {

	public static final String URI = "/rehabdoctor/rehabdoctorregistration";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation representation = null;
		try {
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");
			String firstName = XMLUtils.getTextValue(rootEl, "firstname");
			String lastName = XMLUtils.getTextValue(rootEl, "lastname");
			String registrationKey = XMLUtils.getTextValue(rootEl, "registrationkey");

			// output
			representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.

			String res = "";
			try {
				ObjectifyService.register(RehabDoctor.class);
			} catch (Exception e) {
			}

			Objectify ofy = ObjectifyService.begin();
			RehabDoctor us = ofy.query(RehabDoctor.class)
					.filter("username", username).get();
			if (us != null) {
				res = "doctor already registered";
			} else {
				
				RehabDoctor doctor = new RehabDoctor(username, firstName, lastName);
				ofy.put(doctor);
				res = "OK" + doctor.getUsername();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("result", res);
			Document d = representation.getDocument();
			d = XMLUtils.createXMLResult("rehabdoctorregistrationOutput", map, d);

			
		} catch (IOException e) {
			representation = XMLUtils.createXMLError("doctor registration error",
					"" + e.getMessage());
		}

		// Returns the XML representation of this document.
		return representation;
	}





}
