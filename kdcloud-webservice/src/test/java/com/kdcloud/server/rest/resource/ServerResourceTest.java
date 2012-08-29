package com.kdcloud.server.rest.resource;

import static org.junit.Assert.*;

import java.util.LinkedList;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Dataset;
import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerAction;
import com.kdcloud.server.entity.ServerMethod;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.AnalysisResource;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.server.rest.api.UserDataResource;

public class ServerResourceTest {
	
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	private static final String USER_ID = "tester";
	
	private UserDataServerResource userDataResource = new UserDataServerResource();
	
	private DatasetServerResource datasetResource = new DatasetServerResource();
	
	private ModalitiesServerResource modalitiesResource = new ModalitiesServerResource();
	
	private AnalysisServerResource analysisResource = new AnalysisServerResource() {
		protected String getParameter(ServerParameter serverParameter) {return USER_ID;};
	};
	
	private GlobalDataServerResource globalDataResource = new GlobalDataServerResource();
	
	private GlobalAnalysisServerResource globalAnalysisResource = new GlobalAnalysisServerResource();
	
	private KDEngine stubEngine = new KDEngine() {
		
		@Override
		public Report execute(LinkedList<DataRow> dataset, long workflowId) {
			return new Report();
		}
	};
	
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		User u = new User();
		u.setId(USER_ID);
		userDataResource.user = u;
		datasetResource.user = u;
		modalitiesResource.user = u;
		analysisResource.user = u;
		analysisResource.engine = stubEngine;
		globalDataResource.user = u;
		globalAnalysisResource.user = u;
		globalAnalysisResource.engine = stubEngine;
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}
	
	@Test
	public void testUserData() {
		Long id = userDataResource.createDataset();
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
		Long id = userDataResource.createDataset();
		
		datasetResource.dataset = datasetResource.dataTableDao.findById(id);
		assertNotNull(datasetResource.dataset.getId());
		String[] cells = {"1", "2"};
		DataRow dataRow = new DataRow(cells);
		LinkedList<DataRow> data = new LinkedList<DataRow>();
		data.add(dataRow);
		datasetResource.uploadData(data);
		
//		datasetResource.addCommitter("committer");
//		datasetResource.user = new User();
//		datasetResource.user.setId("committer");
//		datasetResource.uploadData(data);
//		
		datasetResource.user = datasetResource.userDao.findById(USER_ID);
		assertNotNull(userDataResource.user.getTables().iterator().next().getId());
//		assertEquals(2, datasetResource.getData().size());
		
		datasetResource.deleteDataset();
		assertNull(datasetResource.dataTableDao.findById(id));
	}
	
	@Test
	public void testModalities() {
		addStandardModalities();
		assertEquals(3, modalitiesResource.listModalities().size());
	}
	
	@Test
	public void testAnalysis() {
		userDataResource.createDataset();
		Report r = analysisResource.requestAnalysis();
		assertNotNull(r);
	}
	
	@Test
	public void testGlobalData() {
		String[] ids = {"a", "b", "c"};
		for (String s : ids) {
			User user = new User();
			user.setId(s);
			userDataResource.user = user;
			userDataResource.createDataset();
			assertEquals(1, userDataResource.listDataset().size());
		}
		assertTrue(globalDataResource.getAllUsersWithData().contains(ids[0]));
		assertEquals(ids.length, globalDataResource.getAllUsersWithData().size());
		
		assertEquals(ids.length, globalAnalysisResource.requestAnalysis().size());
	}
	
	private void addStandardModalities() {
		Modality dataFeed = new Modality();
		dataFeed.setName("Data Feed");
		dataFeed.getSensors().add("ecg");
		ServerAction createDataset = new ServerAction(UserDataResource.URI,
				ServerParameter.DATASET_ID.getName(), ServerMethod.PUT, false,
				10 * 60 * 1000);
		dataFeed.getServerCommands().add(createDataset);
		ServerAction uploadData = new ServerAction(DatasetResource.URI, null,
				ServerMethod.PUT, true, 10 * 60 * 1000);
		dataFeed.getServerCommands().add(uploadData);
		modalitiesResource.createModality(dataFeed);

		Modality singleAnalysis = new Modality();
		singleAnalysis.setName("Single Analysis");
		ServerAction analyze = new ServerAction(AnalysisResource.URI, null,
				ServerMethod.GET, false, 0);
		singleAnalysis.getServerCommands().add(analyze);
		modalitiesResource.createModality(singleAnalysis);

		Modality globalAnalysis = new Modality();
		globalAnalysis.setName("Global Analysis");
		ServerAction globalAnalyze = new ServerAction(
				GlobalAnalysisResource.URI, null, ServerMethod.GET, false, 0);
		globalAnalysis.getServerCommands().add(globalAnalyze);
		modalitiesResource.createModality(globalAnalysis);
	}
	
}
