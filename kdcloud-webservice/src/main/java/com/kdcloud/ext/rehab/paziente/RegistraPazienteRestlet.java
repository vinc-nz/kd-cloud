package com.kdcloud.ext.rehab.paziente;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kdcloud.server.rest.resource.KDServerResource;

public class RegistraPazienteRestlet extends KDServerResource {

	public static final String URI = "/rehab/registrapaziente";
	


//	public boolean allowPost() {
//		 return true;
//	}


	@Put
	public Representation storeItem(Representation entity) {

		try {
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");

			// output
			DomRepresentation representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.
			Document d = representation.getDocument();
			Element r = d.createElement("roooot");
			d.appendChild(r);

			Element eltName = d.createElement("nome");
			eltName.appendChild(d.createTextNode(username));
			r.appendChild(eltName);

			d.normalizeDocument();

			// Returns the XML representation of this document.
			return representation;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Post("xml:xml")
	public Representation acceptItem(Representation entity) {

		try {
			DomRepresentation input = new DomRepresentation(entity);
			// input
			Document doc = input.getDocument();
			Element rootEl = doc.getDocumentElement();
			String username = XMLUtils.getTextValue(rootEl, "username");

			// output
			DomRepresentation representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.
			Document d = representation.getDocument();
			Element r = d.createElement("roooot");
			d.appendChild(r);

			Element eltName = d.createElement("nome");
			eltName.appendChild(d.createTextNode(username));
			r.appendChild(eltName);

			d.normalizeDocument();

			// Returns the XML representation of this document.
			return representation;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Get("xml")
	public Representation provaGet() {
		// Generate the right representation according to its media type.
		try {
			DomRepresentation representation = new DomRepresentation(
					MediaType.TEXT_XML);

			// Generate a DOM document representing the list of
			// items.
			Document d = representation.getDocument();
			Element r = d.createElement("items");
			d.appendChild(r);

			Element eltName = d.createElement("name");
			eltName.appendChild(d.createTextNode("Fabrix"));
			r.appendChild(eltName);

			d.normalizeDocument();

			// Returns the XML representation of this document.
			return representation;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
	
	
	
	
	
	// @Post("xml")
	// public DomRepresentation doPost(DomRepresentation d) {
	//
	// // User user = getUser();
	// // String username = user.getName();
	//
	// Document doc = null;
	//
	// try {
	// doc = d.getDocument();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	//
	// }
	//
	// // handle document input
	// Element rootEl = doc.getDocumentElement();
	// String username = XMLUtils.getTextValue(rootEl, "username");
	// String password = XMLUtils.getTextValue(rootEl, "password");
	// String nome = XMLUtils.getTextValue(rootEl, "nome");
	// String cognome = XMLUtils.getTextValue(rootEl, "cognome");
	//
	// String esito = "";
	// try {
	// ObjectifyService.register(Paziente.class);
	// } catch (Exception e) {
	// }
	//
	// Objectify ofy = ObjectifyService.begin();
	// Paziente paz = ofy.query(Paziente.class).filter("username", username)
	// .get();
	// if (paz != null) {
	// esito = "errore";
	// } else {
	//
	// Paziente paziente = new Paziente(username, nome, cognome);
	// ofy.put(paziente);
	// esito = "OK";
	// }
	//
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("esito", esito);
	// Document ris = XMLUtils.createXMLResult("registrapazienteOutput", map);
	//
	// DomRepresentation result = new DomRepresentation(MediaType.TEXT_XML,
	// ris);
	//
	// return result;
	//
	// }

	// @Put
	// public DomRepresentation doPut(DomRepresentation d) {
	//
	// // User user = getUser();
	// // String username = user.getName();
	//
	// Document doc = null;
	//
	// try {
	// doc = d.getDocument();
	//
	// // handle document input
	// Element rootEl = doc.getDocumentElement();
	// String username = XMLUtils.getTextValue(rootEl, "username");
	// String password = XMLUtils.getTextValue(rootEl, "password");
	// String nome = XMLUtils.getTextValue(rootEl, "nome");
	// String cognome = XMLUtils.getTextValue(rootEl, "cognome");
	//
	// String esito = "";
	// try {
	// ObjectifyService.register(Paziente.class);
	// } catch (Exception e) {
	// }
	//
	// Objectify ofy = ObjectifyService.begin();
	// Paziente paz = ofy.query(Paziente.class)
	// .filter("username", username).get();
	// if (paz != null) {
	// esito = "errore";
	// } else {
	//
	// Paziente paziente = new Paziente(username, nome, cognome);
	// ofy.put(paziente);
	// esito = "OK";
	// }
	//
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("esito", esito);
	// Document ris = XMLUtils.createXMLResult("registrapazienteOutput",
	// map);
	//
	// DomRepresentation result = new DomRepresentation(
	// MediaType.TEXT_XML, ris);
	//
	// return result;
	//
	// } catch (Exception ex) {
	// // TODO Auto-generated catch block
	// getLogger().log(Level.SEVERE, ex.toString(), ex);
	// return null;
	// }
	//
	// }

	// @Get
	// public String toString() {
	// return "ciao";
	//
	// }

}
