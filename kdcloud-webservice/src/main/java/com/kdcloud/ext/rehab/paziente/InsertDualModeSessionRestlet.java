package com.kdcloud.ext.rehab.paziente;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.kdcloud.ext.rehab.db.DualModeSession;
import com.kdcloud.ext.rehab.db.CompleteExercise;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertDualModeSessionRestlet extends RehabServerResource {

	public static final String URI = "/rehab/insertdualmodesession";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = rehabUser.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();

			// handle document input
			Element rootEl = doc.getDocumentElement();
			String name = XMLUtils.getTextValue(rootEl, "name");
			int num = XMLUtils.getIntValue(rootEl, "number");

			Date data = new Date();

			try {
				ObjectifyService.register(DualModeSession.class);
				ObjectifyService.register(CompleteExercise.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			DualModeSession dms;
			Key<RehabUser> us = new Key<RehabUser>(RehabUser.class, username);
			

//			List<EsercizioCompleto> l = new ArrayList<EsercizioCompleto>();
//			l = ofy.query(EsercizioCompleto.class)
//					.filter("numero", numeroEsercizio).list();
//			EsercizioCompleto esercizio = null;
//
//			for(EsercizioCompleto es: l){
//				if(es.getPaziente().equals(paz) && es.getNome().equals(es.getNome())){
//					esercizio = es;
//					break;
//				}
//			}
			
			CompleteExercise exercise = ofy.query(CompleteExercise.class)
					.filter("number", num).filter("rehabuser", us)
					.filter("name", name).get();
			
			Key<CompleteExercise> es = new Key<CompleteExercise>(CompleteExercise.class, exercise.getId());

			dms = new DualModeSession();
			dms.setStartDate(data);
			dms.setRehabUser(us);
			dms.setExercise(es);
			ofy.put(dms);

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();
			Map<String, String> map = new HashMap<String, String>();
			map.put("dualmode_saved", "OK");
			d = XMLUtils.createXMLResult("insertdualmodesessionOutput", map, d);

		} catch (Exception e) {
			result = XMLUtils.createXMLError("insert dual mode session error",
					"" + e.getMessage());
		}

		return result;

	}

}
