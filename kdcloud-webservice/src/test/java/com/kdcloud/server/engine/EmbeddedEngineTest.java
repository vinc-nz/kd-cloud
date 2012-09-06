package com.kdcloud.server.engine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.engine.embedded.EmbeddedEngine;
import com.kdcloud.server.engine.embedded.FileDataReader;
import com.kdcloud.server.engine.embedded.QRS;
import com.kdcloud.server.engine.embedded.ReportGenerator;
import com.kdcloud.server.engine.embedded.SequenceFlow;
import com.kdcloud.server.engine.embedded.UserDataReader;
import com.kdcloud.server.engine.embedded.UserDataWriter;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;

public class EmbeddedEngineTest {
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	PersistenceContext pc;
	Workflow workflow;
	KDEngine engine;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();
		pc = pcf.get();
		workflow = new Workflow();
		SequenceFlow flow = new SequenceFlow();
		flow.add(new FileDataReader("ecg_small.txt"));
		flow.add(new UserDataWriter(new User("test")));
		flow.add(new UserDataReader());
		flow.add(new QRS());
		flow.add(new ReportGenerator("view.xml"));
		workflow.setExecutionData(flow);
		engine = new EmbeddedEngine();
	}
	
	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() {
		Worker worker = engine.getWorker(workflow);
		worker.setParameter(ServerParameter.USER_ID, "test");
		worker.setPersistenceContext(pc);
		worker.run();
	}

}
