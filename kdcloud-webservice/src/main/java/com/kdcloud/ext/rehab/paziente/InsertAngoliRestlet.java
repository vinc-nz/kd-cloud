package com.kdcloud.ext.rehab.paziente;

import java.util.Date;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertAngoliRestlet extends KDServerResource {

	public static final String URI = "/rehab/insertangoli";

	@Post
	protected String doPost(Form form) {

		User user = getUser();
		String username = user.getName();

		String timest = form.getFirstValue("timestamp");
		int timestamp = Integer.parseInt(timest);

		String ek = form.getFirstValue("elbowknee"); // TODO fix
		int elbowknee = Integer.parseInt(ek);

		String sr = form.getFirstValue("storeraw"); // TODO fix
		int storeRawData = Integer.parseInt(sr);

		String raw = form.getFirstValue("raw");
		raw = raw.trim();

		Date data = new Date();

		String a = form.getFirstValue("angles");

		try {
			ObjectifyService.register(RawDataPacket.class);
		} catch (Exception e) {
		}
		Objectify ofy = ObjectifyService.begin();
		RawDataPacket r;
		Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
		if (storeRawData == 0)// non salvare i raw data
			r = new RawDataPacket(timestamp, paz, data, null, a, elbowknee);
		else
			r = new RawDataPacket(timestamp, paz, data, raw, a, elbowknee);
		ofy.put(r);

		return " - " + timestamp + " pubblicato ";

	}

}
