package com.kdcloud.server.persistence.gae;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.persistence.PersistenceContextFactory;

public class GAESaverTest {
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
	/* .setDefaultHighRepJobPolicyUnappliedJobPercentage(100) */);
	
	PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();

	@Before
	public void setUp() throws Exception {
		helper.setUp();
	}
	
	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(getClass().getClassLoader().getResourceAsStream("ecg_small.txt"));
		Instances input = loader.getDataSet();
		GAESaver saver = new GAESaver();
		DataTable t = new DataTable();
		t.setName("test");
		pcf.get().save(t);
		saver.save(input, t);
		assertEquals(input.size(), saver.load(t).size());
	}

}
