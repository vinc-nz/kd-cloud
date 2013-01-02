package com.kdcloud.ext.rehab.doctor;

import java.util.ArrayList;
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
import com.kdcloud.ext.rehab.db.RehabDoctor;
import com.kdcloud.ext.rehab.db.RehabUser;
import com.kdcloud.ext.rehab.user.XMLUtils;

public class LoginRehabDoctorRestlet extends RehabDoctorServerResource {

	public static final String URI = "/rehabdoctor/loginrehabdoctor";

	@Post("xml")
	public Representation acceptItem(Representation entity) {

		DomRepresentation result = null;
		Document d = null;
		try {
			String username = rehabDoctor.getUsername();
			DomRepresentation input = new DomRepresentation(entity);
			Document doc = input.getDocument();
			// handle input document
			Element rootEl = doc.getDocumentElement();
			String u = XMLUtils.getTextValue(rootEl, "username");

			// output
			result = new DomRepresentation(MediaType.TEXT_XML);
			d = result.getDocument();

			try {
				ObjectifyService.register(RehabUser.class);
			} catch (Exception e) {
			}
			Objectify ofy = ObjectifyService.begin();
			Key<RehabDoctor> doctor = new Key<RehabDoctor>(RehabDoctor.class,
					username);

			Map<String, String> map = new LinkedHashMap<String, String>();

			if (!username.equals(u)) {
				map.put("login", "doctor login error");
				d = XMLUtils.createXMLResult("loginrehabdoctorOutput", map, d);
			} else {
				Element root = d.createElement("loginrehabdoctorOutput");
				d.appendChild(root);
				root.setAttribute("username", rehabDoctor.getUsername());
				root.setAttribute("firstname", rehabDoctor.getFirstName());
				root.setAttribute("lastname", rehabDoctor.getLastName());

				List<RehabUser> l = new ArrayList<RehabUser>();
				l = ofy.query(RehabUser.class).filter("doctor", doctor)//.order("-date")//data decrescente
						.list();

				Element eltName4 = d.createElement("rehab_users_list");
				for (RehabUser us : l) {
					Element userElement = d.createElement("user");
					userElement.setAttribute("username", "" + us.getUsername());
					userElement.setAttribute("firstname", "" + us.getFirstName());
					userElement.setAttribute("lastname", "" + us.getLastName());
					userElement.setAttribute("registrationdate", "" + us.getRegistrationDate().toGMTString());
					eltName4.appendChild(userElement);
				}
				root.appendChild(eltName4);

				d.normalizeDocument();
			}

		} catch (Exception e) {
			result = XMLUtils
					.createXMLError("doctor login error", "" + e.getMessage());
		}

		return result;
	}

}
