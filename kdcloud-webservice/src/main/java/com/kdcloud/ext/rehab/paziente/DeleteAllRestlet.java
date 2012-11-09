package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DeleteAllRestlet extends KDServerResource {

	public static final String URI = "/rehab/deleteall";

	@Post
	protected String doPost(Form form) {

		return deleteAll();
		
	}
	
	private String deleteAll() {

		try {
			ObjectifyService.register(RawDataPacket.class);
		} catch (Exception e) {

		}
		try {
			Objectify ofy = ObjectifyService.begin();
			Iterable<Key<RawDataPacket>> allKeys = ofy.query(
					RawDataPacket.class).fetchKeys();
			ofy.delete(allKeys);
			return "deleted";
		} catch (Exception e) {
			return "errore" + e;
		}

	}

}
