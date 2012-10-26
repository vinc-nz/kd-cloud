package com.kdcloud.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;

public class EmbeddedEngineTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	PersistenceContext pc;
	String[] descriptions = {"test-workflow.xml", "ecg.xml"};
	KDEngine engine;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();
		pc = pcf.get();
		pc.getUserDao().save(new User("test"));
		pc.getGroupDao().save(new Group("test"));
		engine = new EmbeddedEngine();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() throws Exception {
		for (String desc: descriptions) {
			URI uri = getClass().getClassLoader().getResource(desc).toURI();
			InputStream is = new FileInputStream(new File(uri));
			Worker worker = engine.getWorker(is);
			worker.setParameter(PersistenceContext.class.getName(), pc);
			worker.setParameter(ServerParameter.USER_ID.getName(), "test");
			worker.setParameter(ServerParameter.GROUP_ID.getName(), "test");
			assertTrue(worker.configure());
			worker.run();
			assertEquals(Worker.STATUS_JOB_COMPLETED, worker.getStatus());
		}
	}

}
