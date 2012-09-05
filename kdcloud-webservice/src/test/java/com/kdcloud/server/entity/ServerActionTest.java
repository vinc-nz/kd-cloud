package com.kdcloud.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kdcloud.server.rest.api.DatasetResource;

public class ServerActionTest {

	@Test
	public void test() {
		ServerAction action = 
				new ServerAction(DatasetResource.URI, null, ServerMethod.PUT, false, 0);
		action.addParameter(ServerParameter.USER_ID);
		ServerParameter param = action.uriParams.iterator().next();
		assertEquals(ServerParameter.DATASET_ID.getName(), param.getName());
		ServerAction newAction = action.setParameter(param, "1");
		assertEquals("/data/1", newAction.uri);
		newAction = action.setParameter(ServerParameter.USER_ID, "1");
		assertTrue(action.postForm != newAction.postForm);
	}
}
