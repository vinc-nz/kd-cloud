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

public class DownloadUserRehabDaysRestlet extends RehabDoctorServerResource {// KDServerResource
																				// {
																				// //

	public static final String URI = "/rehabdoctor/downloaduserrehabdays";

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

				Element root = d.createElement("downloaduserrehabdays");
				d.appendChild(root);
				int count = 0;
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
					datalistEl.setAttribute("date", ""
							+ dat.toGMTString());

					root.appendChild(datalistEl);
				}
				d.normalizeDocument();

			} else
				result = XMLUtils.createXMLError(
						"download buffered data error", "no exercises found");

		} catch (Exception e) {
			result = XMLUtils.createXMLError("download buffered data error", ""
					+ e.getMessage());
		}

		return result;
	}

}
