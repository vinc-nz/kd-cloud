package com.kdcloud.lib.domain;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Form;

import com.kdcloud.lib.domain.ServerAction;
import com.kdcloud.lib.domain.ServerMethod;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.WorkflowResource;

public class ServerActionTest {

	@Test
	public void test() {
		ServerAction action = new ServerAction(WorkflowResource.URI, null,
				ServerMethod.GET, false, 0);
		Assert.assertEquals(1, action.uriParams.size());
		Assert.assertTrue(action.uriParams.contains(ServerParameter.WORKFLOW_ID));
		action = action.setParameter(ServerParameter.WORKFLOW_ID, "1");
		Assert.assertEquals("/workflow/1", action.uri);
		action.addParameter(ServerParameter.USER_ID);
		action = action.setParameter(ServerParameter.USER_ID, "test");
		Assert.assertEquals("test", new Form(action.postForm).getFirstValue("userId"));
	}
}
