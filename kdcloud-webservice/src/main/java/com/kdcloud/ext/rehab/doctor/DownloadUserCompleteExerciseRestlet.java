package com.kdcloud.ext.rehab.doctor;

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
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadUserCompleteExerciseRestlet extends  RehabDoctorServerResource{// KDServerResource { //
																	

	public static final String URI = "/rehabdoctor/downloadusercompleteexercise";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");
			int num = XMLUtils.getIntValue(rootEl, "number");
			String name = XMLUtils.getTextValue(rootEl, "name");

			try {
				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

			
			CompleteExercise exercise = ofy.query(CompleteExercise.class)
					.filter("number", num).filter("rehabuser", us)
					.filter("name", name).get();
			
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			if(exercise != null)
				d = exercise.toXMLDocument(d);
			else
				result = XMLUtils.createXMLError("download user complete exercise error", "no exercises found");
			


		} catch (Exception e) {
			result = XMLUtils.createXMLError("download complete exercise error", ""
					+ e.getMessage());
		}

		return result;
	}


}
