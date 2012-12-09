package com.kdcloud.server.rest.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.ext.xml.XmlRepresentation;
import org.restlet.resource.ClientResource;

import com.kdcloud.lib.domain.GroupSpecification;
import com.kdcloud.lib.domain.Index;
import com.kdcloud.server.rest.application.ConvertHelper;

public class TestUnmarshalling {

	public Object unmarshal(String resource, Class<?> clazz) {
		try {
			//File schemaFile = new File("src/main/webapp/api/kdcloud.xsd");
			LocalReference local = new LocalReference("src/main/webapp/api/kdcloud.xsd");
			local.setProtocol(Protocol.FILE);
			ClientResource localCr = new ClientResource(local);
			SAXSource schemaFile = XmlRepresentation.getSaxSource(localCr.get());
			Schema schema = SchemaFactory.newInstance(
					XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaFile);
			LocalReference ref = new LocalReference(resource);
			ref.setProtocol(Protocol.CLAP);
			ClientResource cr = new ClientResource(ref);
			return ConvertHelper.toObject(clazz, cr.get(), schema);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}
	
	@Test
	public void testGroupSpecification() {
		GroupSpecification spec = (GroupSpecification) unmarshal("group.xml", GroupSpecification.class);
		assertNotNull(spec.getMetadata());
		assertNotNull(spec.getDataSpecification());
		assertNotNull(spec.getInvitationMessage());
	}
	
	@Test
	public void testIndex() {
		Index index = (Index) unmarshal("workflow/index.xml", Index.class);
		assertEquals(1, index.size());
	}

}
