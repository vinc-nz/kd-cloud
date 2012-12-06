package com.kdcloud.ext.rehab.doctor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import com.kdcloud.ext.rehab.db.BufferedData;
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadUserBufferedDataRestlet extends RehabDoctorServerResource {// KDServerResource
																				// {
																				// //

	public static final String URI = "/rehabdoctor/downloaduserbuffereddata";

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
			String str_date = XMLUtils.getTextValue(rootEl, "date");
			int lenght = XMLUtils.getIntValue(rootEl, "lenght");
			DateFormat formatter;
			Date da;
			formatter = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
			da = (Date) formatter.parse(str_date);

			try {
				ObjectifyService.register(BufferedData.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

			List<BufferedData> dataList = ofy.query(BufferedData.class)
					.filter("rehabuser", us).filter("insertdate  >", da)
					.order("insertdate").limit(lenght / 10).list();

			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			if (dataList != null) {

				Element root = d.createElement("downloaduserbuffereddataOutput");
				d.appendChild(root);
				for (BufferedData b : dataList) {
					Element datalistEl = d.createElement("buffered_data");
					
					datalistEl.setAttribute("elbowknee", "" + b.getElbowknee());
					datalistEl.setAttribute("date", "" + b.getInsertDate().toGMTString());
					datalistEl.setAttribute("lenght", "" + b.getLenght());
					int i = 0;
					for (Integer[] raw_sample : b.getRaw()) {
						Element rawdata = d.createElement("raw_data");
						rawdata.setAttribute("timestamp", "" + i++);
						rawdata.setAttribute("bx", "" + raw_sample[0]);
						rawdata.setAttribute("by", "" + raw_sample[1]);
						rawdata.setAttribute("bz", "" + raw_sample[2]);
						rawdata.setAttribute("fx", "" + raw_sample[3]);
						rawdata.setAttribute("fy", "" + raw_sample[4]);
						rawdata.setAttribute("fz", "" + raw_sample[5]);
						datalistEl.appendChild(rawdata);
					}
					i = 0;
					for (Integer[] angle_sample : b.getAngles()) {
						Element angle = d.createElement("angles_data");
						angle.setAttribute("timestamp", "" + i++);
						angle.setAttribute("elbowknee", "" + angle_sample[0]);
						angle.setAttribute("backline", "" + angle_sample[1]);
						angle.setAttribute("foreline", "" + angle_sample[2]);
						angle.setAttribute("sideangle", "" + angle_sample[3]);
						datalistEl.appendChild(angle);
					}
					root.appendChild(datalistEl);
				}
				d.normalizeDocument();

			} else
				result = XMLUtils.createXMLError(
						"download user complete exercise error",
						"no exercises found");

		} catch (Exception e) {
			result = XMLUtils.createXMLError(
					"download complete exercise error", "" + e.getMessage());
		}

		return result;
	}

}
