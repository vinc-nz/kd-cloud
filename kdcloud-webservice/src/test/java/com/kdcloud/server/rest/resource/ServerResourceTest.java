package com.kdcloud.server.rest.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.tasks.TaskQueue;

public class ServerResourceTest {
	
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	private static final String USER_ID = "tester";
	
	UserDataServerResource userDataResource = new UserDataServerResource();
	DatasetServerResource datasetResource = new DatasetServerResource();
	SchedulerServerResource schedulerResource = new SchedulerServerResource();
	
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		User u = new User();
		u.setId(USER_ID);
		userDataResource.user = u;
		datasetResource.user = u;
		schedulerResource.user = u;
		schedulerResource.taskQueue = new TaskQueue() {
			
			@Override
			public void push(Task task) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}
	
	@Test
	public void testUserData() {
		Long id = userDataResource.createDataset("test", "test");
		assertNotNull(id);
		
		User u = userDataResource.userDao.findById(USER_ID);
		assertNotNull(u);
		
		assertEquals(1, userDataResource.listDataset().size());
		
		userDataResource.deleteAllData();
		u = userDataResource.userDao.findById(USER_ID);
		assertNull(u);
		DataTable dataTable = userDataResource.dataTableDao.findById(id);
		assertNull(dataTable);
	}

	@Test
	public void testDataset() {
		Long id = userDataResource.createDataset("test", "test");
		
		datasetResource.dataset = datasetResource.dataTableDao.findById(id);
		String[] cells = {"1", "2"};
		DataRow dataRow = new DataRow(cells);
		LinkedList<DataRow> data = new LinkedList<DataRow>();
		data.add(dataRow);
		datasetResource.uploadData(data);
		
		datasetResource.addCommitter("committer");
		datasetResource.user = new User();
		datasetResource.user.setId("committer");
		datasetResource.uploadData(data);
		
		datasetResource.user = new User();
		datasetResource.user.setId(USER_ID);
		assertEquals(2, datasetResource.getData().size());
		
		datasetResource.deleteDataset();
		assertNull(datasetResource.dataTableDao.findById(id));
	}
	
}
