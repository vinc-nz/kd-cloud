package com.kdcloud.ext.rehab.paziente;

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
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadCompleteExerciseRestlet extends  RehabServerResource{// KDServerResource { //
																	

	public static final String URI = "/rehab/downloadcompleteexercise";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			// String username = paziente.getUsername();
			User user = getUser();
			String username = user.getName();

			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			int num = XMLUtils.getIntValue(rootEl, "number");
			String name = XMLUtils.getTextValue(rootEl, "name");

			try {
				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);

//			List<EsercizioCompleto> l = new ArrayList<EsercizioCompleto>();
//			l = ofy.query(EsercizioCompleto.class)
//					.filter("numero", numero).list();
//			EsercizioCompleto esercizio = null;
//
//			for(EsercizioCompleto es: l){
//				if(es.getPaziente().equals(paz) && nome.equals(es.getNome())){
//					esercizio = es;
//					break;
//				}
//			}
			
			CompleteExercise exercise = ofy.query(CompleteExercise.class)
					.filter("number", num).filter("rehabuser", us)
					.filter("name", name).get();
			
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			
			if(exercise != null)
				d = exercise.toXMLDocument(d);
			else
				result = XMLUtils.createXMLError("download complete exercise error", "no exercises found");
			


		} catch (Exception e) {
			result = XMLUtils.createXMLError("download complete exercise error", ""
					+ e.getMessage());
		}

		return result;
	}


}
