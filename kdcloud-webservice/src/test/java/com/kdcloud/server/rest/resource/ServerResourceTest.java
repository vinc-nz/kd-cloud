/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.representation.Representation;

import weka.core.DenseInstance;
import weka.core.Instances;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.engine.embedded.node.UserDataReader;
import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.Group;

public class ServerResourceTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
	/* .setDefaultHighRepJobPolicyUnappliedJobPercentage(100) */);

	public static final String USER_ID = TestContext.USER_ID;
	
	private Context context = new TestContext();

	private Application application = new Application(context);
	
	private Group group;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		GroupServerResource resource = new GroupServerResource(application, "test");
		resource.create(null);
		group = resource.find();
		assertNotNull(group);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testDataset() {
		DatasetServerResource datasetResource = new DatasetServerResource(application, group.getName());
		double[] cells = { 1, 2 };
		Instances data = new Instances(DataSpecification.newInstances("test", 2));
		data.add(new DenseInstance(0, cells));
		datasetResource.uploadData(new InstancesRepresentation(data));
		
		Representation out = datasetResource.getData();
		try {
			assertEquals(1, new InstancesRepresentation(out).getInstances().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		datasetResource.deleteData();
		group = datasetResource.findGroup();
		Assert.assertEquals(0, group.getData().size());
	}

	@Test
	public void testModalities() {
		ModalitiesServerResource modalitiesResource = new ModalitiesServerResource(application);
		ModalityIndex index = modalitiesResource.listModalities();
		Assert.assertNotNull(index);
//
//		ModEntity modality = list.get(0);
//		modality.setName("test");
//		ChoosenModalityServerResource choosenModalityResource = new ChoosenModalityServerResource(application, modality);
//		choosenModalityResource.editModality(modality);
//		modality = choosenModalityResource.modalityDao.findById(modality.getId());
//		assertEquals("test", modality.getName());
//		
//		choosenModalityResource.deleteModality();
//		modality = choosenModalityResource.modalityDao.findById(modality.getId());
//		assertNull(modality);
	}

	@Test
	public void testWorker() {
		Instances instances = DataSpecification.newInstances("test", 1);
		DatasetServerResource resource = new DatasetServerResource(application, group.getName());
		resource.uploadData(new InstancesRepresentation(instances));
		WorkerServerResource workflowResource = new WorkerServerResource(application, "ecg.xml");
		Form form = new Form();
		form.add(UserDataReader.SOURCE_USER_PARAMETER, USER_ID);
		form.add(UserDataReader.SOURCE_GROUP_PARAMETER, "test");
		InputStream is = getClass().getClassLoader().getResourceAsStream("ecg.xml");
		try {
			workflowResource.execute(form, is);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}


	@Test
	public void testDevices() {
		DeviceServerResource deviceResource = new DeviceServerResource(application);
		deviceResource.register("test");
		deviceResource.unregister("test");
	}


}
