package com.kdcloud.server.rest.resource;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.kdcloud.engine.embedded.WorkflowDescription;
import com.kdcloud.lib.domain.GroupSpecification;
import com.kdcloud.lib.domain.Index;
import com.kdcloud.server.rest.application.ConvertUtils;

public class TestUnmarshalling {

	public Object unmarshal(String resource, Class<?> clazz) {
		LocalReference ref = new LocalReference(resource);
		ref.setProtocol(Protocol.CLAP);
		ClientResource cr = new ClientResource(ref);
		try {
			return ConvertUtils.toObject(clazz, cr.get());
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
