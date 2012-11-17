package com.kdcloud.ext.rehab.paziente;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.kdcloud.server.rest.application.RestletTestCase;

public class Test1 extends RestletTestCase {

	@Test
	public void provaPost() {
		try {
			System.out.println("********* POST");

			// input da inviare
			DomRepresentation representation = new DomRepresentation(
					MediaType.TEXT_XML);
			Document d = representation.getDocument();
			Element r = d.createElement("items");
			d.appendChild(r);

			Element eltName = d.createElement("username");
			eltName.appendChild(d.createTextNode("aabbbaaa"));
			r.appendChild(eltName);

			d.normalizeDocument();
			// String xmlString = transformXMLToString(d);
			ClientResource cr = new ClientResource(getServerUrl()
					+ "/rehab/registrapaziente");
			cr.setRequestEntityBuffering(true);

			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "aaa", "bbb");

			String input = transformXMLToString(d);
			System.out.println("input = " + input);

			Representation s = cr.post(representation);
			System.out.println("representation = " + s.toString());
			System.out.println("media type = " + s.getMediaType());
			DomRepresentation ricevuto = new DomRepresentation(s);

			System.out.println("dom representation = " + ricevuto.toString());
			Document doc = null;
			try {
				doc = ricevuto.getDocument();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("document = " + d.toString());
			String output = transformXMLToString(doc);
			System.out.println("output = " + output);
			assertNotNull(s);

		} catch (Exception e) {
			System.out.println("eccezione post " + e);

		}

	}
	
	
//	@Test
//	public void provaPut() {
//		try {
//			System.out.println("********* PUT");
//
//			// input da inviare
//			DomRepresentation representation = new DomRepresentation(
//					MediaType.TEXT_XML);
//			Document d = representation.getDocument();
//			Element r = d.createElement("items");
//			d.appendChild(r);
//
//			Element eltName = d.createElement("username");
//			eltName.appendChild(d.createTextNode("aabbbaaa"));
//			r.appendChild(eltName);
//
//			d.normalizeDocument();
//			// String xmlString = transformXMLToString(d);
//			ClientResource cr = new ClientResource(getServerUrl()
//					+ "/rehab/registrapaziente");
//			cr.setRequestEntityBuffering(true);
//
//			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "aaa", "bbb");
//
//			String input = transformXMLToString(d);
//			System.out.println("input = " + input);
//
//			Representation s = cr.put(representation);
//			System.out.println("representation = " + s.toString());
//			System.out.println("media type = " + s.getMediaType());
//			DomRepresentation ricevuto = new DomRepresentation(s);
//
//			System.out.println("dom representation = " + ricevuto.toString());
//			Document doc = null;
//			try {
//				doc = ricevuto.getDocument();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("document = " + d.toString());
//			String output = transformXMLToString(doc);
//			System.out.println("output = " + output);
//			assertNotNull(s);
//
//		} catch (Exception e) {
//			System.out.println("eccezione post " + e);
//
//		}
//
//	}



//	@Test
//	public void provaGet() {
//		System.out.println("********* GET");
//		ClientResource cr = new ClientResource(getServerUrl()
//				+ "/rehab/registrapaziente");
//		cr.setRequestEntityBuffering(true);
//
//		cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "aaa", "bbb");
//		Representation s = cr.get(MediaType.TEXT_XML);
//		// try {
//		// //System.out.println("stream = "+s.getStream());
//		// System.out.println("get text = "+s.getText());
//		// } catch (IOException e1) {
//		// // TODO Auto-generated catch block
//		// e1.printStackTrace();
//		// }
//
//		System.out.println("representation = " + s.toString());
//		System.out.println("media type = " + s.getMediaType());
//		DomRepresentation ricevuto = new DomRepresentation(s);
//
//		System.out.println("dom representation = " + ricevuto.toString());
//		Document d = null;
//		try {
//			d = ricevuto.getDocument();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("document = " + d.toString());
//		String output = transformXMLToString(d);
//		System.out.println("output = " + output);
//		assertNotNull(s);
//	}

	
	
	

	private String transformXMLToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		return output;
	}

	private DomRepresentation createDocument() {
		DomRepresentation result = null;
		// This is an error
		// Generate the output representation
		try {
			result = new DomRepresentation(MediaType.TEXT_XML);
			// Generate a DOM document representing the list of
			// items.
			Document doc = result.getDocument();
			Element root = doc.createElement("registrapazienteInput");
			doc.appendChild(root);

			Element child = doc.createElement("username");
			Text text = doc.createTextNode("fabrix");
			child.appendChild(text);
			root.appendChild(child);
			child = doc.createElement("password");
			text = doc.createTextNode("ciao");
			child.appendChild(text);
			root.appendChild(child);
			child = doc.createElement("nome");
			text = doc.createTextNode("Fabrizio");
			child.appendChild(text);
			root.appendChild(child);
			child = doc.createElement("cognome");
			text = doc.createTextNode("Granieri");
			child.appendChild(text);
			root.appendChild(child);

			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
