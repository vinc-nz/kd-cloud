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
import com.kdcloud.ext.rehab.db.DualModeSession;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadUserDualModeSessionsRestlet extends  RehabDoctorServerResource{// KDServerResource { //
																	

	public static final String URI = "/rehabdoctor/downloaduserdualmodesessions";

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

			try {
				ObjectifyService.register(DualModeSession.class);
				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

			
			List<DualModeSession> sessions = ofy.query(DualModeSession.class).
					filter("rehabuser", us).list();
			
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			if(sessions != null && sessions.size() > 0){

				Element root = d.createElement("downloaduserdualmodesessionsOutput");
				root.setAttribute("username", "" + username);
				d.appendChild(root);

				Element eltName4 = d.createElement("sessions_list");
				for (DualModeSession session : sessions) {
					Element ses = d.createElement("session");
					ses.setAttribute("startdate", "" + session.getStartDate().toGMTString());
					CompleteExercise ex = ofy.get(session.getExercise());
					ses.setAttribute("name", "" + ex.getName());
					ses.setAttribute("number", "" + ex.getNumber());
					eltName4.appendChild(ses);
				}
				root.appendChild(eltName4);

				d.normalizeDocument();
			}else
				result = XMLUtils.createXMLError("download user sessions error", "no sessions found");
			


		} catch (Exception e) {
			result = XMLUtils.createXMLError("download user sessions error", ""
					+ e.getMessage());
		}

		return result;
	}


}
