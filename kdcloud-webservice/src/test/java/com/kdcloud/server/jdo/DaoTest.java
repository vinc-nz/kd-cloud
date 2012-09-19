package com.kdcloud.server.jdo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.lib.domain.Modality;
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
		try {
			Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			file.writeObject(dom);
			pc.getVirtualDirectoryDao().save(dir);
			file = pc.getVirtualDirectoryDao().findFileByName(dir, "test");
			Document m = (Document) file.readObject();
			assertNotNull(m);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
