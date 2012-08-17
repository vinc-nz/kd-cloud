package com.kdcloud.server.rest.resource;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Dataset;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.UserDataResource;

public class ServerResourceTest {
	
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	private static final String USER_ID = "tester";
	
	UserDataServerResource userDataResource = new UserDataServerResource();
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		User u = new User();
		u.setId(USER_ID);
		userDataResource.user = u;
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void test() {
		Long id = userDataResource.createDataset("test", "test");
		assertNotNull(id);
		
		User u = userDataResource.userDao.findById(USER_ID);
		assertNotNull(u);
		
		List<Dataset> tables = userDataResource.listDataset();
		assertEquals(1, tables.size());
		
		userDataResource.deleteAllData();
		u = userDataResource.userDao.findById(USER_ID);
		assertNull(u);
		DataTable dataTable = userDataResource.dataTableDao.findById(id);
		assertNull(dataTable);
	}

}
