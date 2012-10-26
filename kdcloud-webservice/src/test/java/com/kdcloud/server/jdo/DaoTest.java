package com.kdcloud.server.jdo;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;

public class DaoTest {
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					/*.setDefaultHighRepJobPolicyUnappliedJobPercentage(100)*/);

	PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();

	@Before
	public void setUp() throws Exception {
		helper.setUp();
	}

	@Test
	public void test() {
		PersistenceContext pc = pcf.get();
		VirtualDirectory dir = new VirtualDirectory("test");
		dir.getFiles().add(new VirtualFile("test"));
		pc.getVirtualDirectoryDao().save(dir);
		VirtualFile file = pc.getVirtualDirectoryDao().findFileByName(dir, "test");
		assertNotNull(file);
	}

}
