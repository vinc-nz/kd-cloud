package com.kdcloud.server.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.engine.embedded.EmbeddedEngine;
import com.kdcloud.server.engine.embedded.FileDataReader;
import com.kdcloud.server.engine.embedded.SequenceFlow;
import com.kdcloud.server.engine.embedded.UserDataWriter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;

public class EmbeddedEngineTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	PersistenceContext pc;
	Workflow w1;
	Workflow w2;
	KDEngine engine;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();
		pc = pcf.get();
		pc.getUserDao().save(new User("test"));
		w1 = new Workflow();
		SequenceFlow flow1 = new SequenceFlow();
		flow1.add(new FileDataReader("ecg_small.txt"));
		flow1.add(new UserDataWriter());
		w1.setExecutionData(flow1);
		w2 = EmbeddedEngine.getQRSWorkflow();
		engine = new EmbeddedEngine();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() {
		Workflow[] workflows = { w1, w2 };
		for (Workflow workflow : workflows) {
			Worker worker = engine.getWorker(workflow);
			assertEquals(1, worker.getParameters().size());
			worker.setPersistenceContext(pc);
			worker.setParameter(ServerParameter.USER_ID, "test");
			assertTrue(worker.configure());
			worker.run();
			assertEquals(Worker.STATUS_JOB_COMPLETED, worker.getStatus());
		}
	}

}
