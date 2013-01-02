package com.kdcloud.ext.rehab.doctor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.kdcloud.ext.rehab.db.DualModeSession;
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.db.UserScheduling;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class AddUserSchedulingRestlet extends RehabDoctorServerResource {

	public static final String URI = "/rehabdoctor/adduserscheduling";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			
			try {
				ObjectifyService.register(CompleteExercise.class);
				
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();

			// handle document input
			Element rootEl = doc.getDocumentElement();
			String u = XMLUtils.getTextValue(rootEl, "username");
			DateFormat formatter;
			// formatter = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
			// 29 Nov 2012 16:13:18 GMT
			formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss z");
			
			List<UserScheduling> schedulingList = new LinkedList<UserScheduling>();
			NodeList taskList = rootEl.getElementsByTagName("task");
			if (taskList != null && taskList.getLength() > 0) {
				for (int i = 0; i < taskList.getLength(); i++) {
					Element el = (Element) taskList.item(i);
					
					String str_start_date = el.getAttribute("startdate");
					String str_end_date = el.getAttribute("enddate");					
					Date startDate = (Date) formatter.parse(str_start_date);
					Date endDate = (Date) formatter.parse(str_end_date);
					
					int exercise_number = Integer.parseInt(el.getAttribute("exercise_number"));
					String exercise_name = el.getAttribute("exercise_name");
					
					Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, u);				
					CompleteExercise exercise = ofy.query(CompleteExercise.class)
							.filter("number", exercise_number).filter("rehabuser", us)
							.filter("name", exercise_name).get();
					
					Key<CompleteExercise> ex = new Key<CompleteExercise>(CompleteExercise.class, exercise.getId());
					
					
					
					UserScheduling s = new UserScheduling();
					s.setStartDate(startDate);
					s.setEndDate(endDate);
					s.setUser(us);
					s.setExercise(ex);
					schedulingList.add(s);
					

				}
				
				
				try {
					ObjectifyService.register(UserScheduling.class);
					
				} catch (Exception e) {
				}
				ofy = ObjectifyService.begin();
				for(UserScheduling s: schedulingList){
					ofy.put(s);
				}			

			}

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_scheduling_saved", "OK");
			d = XMLUtils.createXMLResult("adduserschedulingOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("add user scheduling error",
					"" + e.getMessage());
		}

		return result;

	}

}
