package com.kdcloud.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.restlet.data.Form;

import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.WorkflowResource;

public class ServerActionTest {

	@Test
	public void test() {
		ServerAction action = new ServerAction(WorkflowResource.URI, null,
				ServerMethod.GET, false, 0);
		assertEquals(1, action.uriParams.size());
		assertTrue(action.uriParams.contains(ServerParameter.WORKFLOW_ID));
		action = action.setParameter(ServerParameter.WORKFLOW_ID, "1");
		assertEquals("/workflow/1", action.uri);
		action.addParameter(ServerParameter.USER_ID);
		action = action.setParameter(ServerParameter.USER_ID, "test");
		assertEquals("test", new Form(action.postForm).getFirstValue("userId"));
	}
}
