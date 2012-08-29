package com.kdcloud.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kdcloud.server.rest.api.DatasetResource;

public class ServerActionTest {

	@Test
	public void test() {
		ServerAction action = 
				new ServerAction(DatasetResource.URI, null, ServerMethod.PUT, false, 0);
		ServerParameter param = action.getParams().iterator().next();
		assertEquals(ServerParameter.DATASET_ID.getName(), param.getName());
		ServerAction newAction = action.setParameter(param, "1");
		assertEquals("/data/1", newAction.uri);
	}
}
