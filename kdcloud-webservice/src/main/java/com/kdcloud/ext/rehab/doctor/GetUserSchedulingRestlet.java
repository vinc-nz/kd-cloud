package com.kdcloud.ext.rehab.doctor;

import java.util.List;

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
import com.kdcloud.ext.rehab.db.UserScheduling;
import com.kdcloud.ext.rehab.user.XMLUtils;

public class GetUserSchedulingRestlet extends RehabDoctorServerResource {

	public static final String URI = "/rehabdoctor/getuserscheduling";

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

				ObjectifyService.register(UserScheduling.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, u);

			List<UserScheduling> schedulingList = ofy
					.query(UserScheduling.class).filter("user", us)
					.order("startDate").list();

			try {

				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			ofy = ObjectifyService.begin();

			if (schedulingList != null) {

				if (schedulingList.size() > 0) {
					Element root = d.createElement("getuserschedulingOutput");
					d.appendChild(root);

					for (UserScheduling s : schedulingList) {
						Element taskEl = d.createElement("task");
						taskEl.setAttribute("startdate", s.getStartDate()
								.toGMTString());
						taskEl.setAttribute("enddate", s.getEndDate()
								.toGMTString());
						taskEl.setAttribute("user", u);
						CompleteExercise ex = ofy.get(s.getExercise());
						taskEl.setAttribute("exercise_name", ex.getName());
						taskEl.setAttribute("exercise_number",
								"" + ex.getNumber());
						root.appendChild(taskEl);
					}

					d.normalizeDocument();
				} else {
					result = XMLUtils.createXMLError("get user scheduling",
							"list size = 0");
				}
			} else {
				result = XMLUtils.createXMLError("get user scheduling",
						"list = null");
			}

		} catch (Exception e) {
			result = XMLUtils.createXMLError("get user scheduling",
					"" + e.getMessage());
		}

		return result;
	}

}
