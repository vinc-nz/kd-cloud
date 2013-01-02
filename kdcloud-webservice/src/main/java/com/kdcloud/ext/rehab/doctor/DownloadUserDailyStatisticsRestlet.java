package com.kdcloud.ext.rehab.doctor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.BufferedData;
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadUserDailyStatisticsRestlet extends
		RehabDoctorServerResource {// KDServerResource
	// {
	// //

	public static final String URI = "/rehabdoctor/downloaduserdailystatistics";

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
				ObjectifyService.register(BufferedData.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

			List<BufferedData> dataList = ofy.query(BufferedData.class)
					.filter("rehabuser", us)// .filter("insertdate  >", da)
					.order("insertdate").list();

			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			Set<Date> days = new TreeSet<Date>();
			if (dataList != null) {

				Element root = d
						.createElement("downloaduserdailystatisticsOutput");
				d.appendChild(root);
				for (BufferedData b : dataList) {
					Date insDate = b.getInsertDate();
					Date day = new Date(insDate.getYear(), insDate.getMonth(),
							insDate.getDate());
					days.add(day);
				}

				List<Date> dayList = new LinkedList<Date>(days);
				Collections.sort(dayList);
				
				
				for (Date dat : dayList) {
					Element datalistEl = d.createElement("rehab_day");
					datalistEl.setAttribute("date", "" + dat.toGMTString());

					Date startOfDay = dat;
					Date endOfDay = new Date(dat.getYear(), dat.getMonth(),
							dat.getDate(), 23, 59, 59);
					int minElbowknee = Integer.MAX_VALUE;
					int maxElbowknee = Integer.MIN_VALUE;
					int rangeElbowknee = 0;
					int minBackline = Integer.MAX_VALUE;
					int maxBackline = Integer.MIN_VALUE;
					int rangeBackline = 0;
					int minSideangle = Integer.MAX_VALUE;
					int maxSideangle = Integer.MIN_VALUE;
					int rangeSideangle = 0;
					int elbkn = -1;
					int length = 0;

					for (BufferedData b : dataList) {
						if (b.getInsertDate().after(startOfDay)
								&& b.getInsertDate().before(endOfDay)) {

							elbkn = b.getElbowknee();
							length += b.getAngles().size();
							int i = 0;

							for (Integer[] angle_sample : b.getAngles()) {
								int elbowknee = 180 - angle_sample[0];
								if(elbkn == 0)
									elbowknee = - elbowknee;
								
								if (elbowknee > maxElbowknee)
									maxElbowknee = elbowknee;
								if (elbowknee < minElbowknee)
									minElbowknee = elbowknee;

								int backline = - angle_sample[1];
								if (backline > maxBackline)
									maxBackline = backline;
								if (backline < minBackline)
									minBackline = backline;

								int sideangle = angle_sample[3] - 90;
								
								if (sideangle > maxSideangle)
									maxSideangle = sideangle;
								if (sideangle < minSideangle)
									minSideangle = sideangle;
							}

						}

					}

					datalistEl.setAttribute("elbowknee", "" + elbkn);
					datalistEl.setAttribute("length", "" + length);
					datalistEl.setAttribute("minElbowknee", "" + minElbowknee);
					datalistEl.setAttribute("maxElbowknee", "" + maxElbowknee);
					rangeElbowknee = maxElbowknee - minElbowknee;
					datalistEl.setAttribute("rangeElbowknee", "" + rangeElbowknee);
					
					datalistEl.setAttribute("minBackline", "" + minBackline);
					datalistEl.setAttribute("maxBackline", "" + maxBackline);
					rangeBackline = maxBackline - minBackline;
					datalistEl.setAttribute("rangeBackline", "" + rangeBackline);
					
					datalistEl.setAttribute("minSideangle", "" + minSideangle);
					datalistEl.setAttribute("maxSideangle", "" + maxSideangle);
					rangeSideangle = maxSideangle - minSideangle;
					datalistEl.setAttribute("rangeSideangle", "" + rangeSideangle);
					root.appendChild(datalistEl);
				}
				d.normalizeDocument();

			} else
				result = XMLUtils.createXMLError(
						"download statistics error", "no statistics found");

		} catch (Exception e) {
			result = XMLUtils.createXMLError("download statistics error", ""
					+ e.getMessage());
		}

		return result;
	}

}
