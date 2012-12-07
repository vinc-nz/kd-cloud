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

import java.io.IOException;

import org.junit.Test;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.rest.application.RestletTestCase;

public class ServerResourceTest extends RestletTestCase {

	@Test
	public void testGroupResource() {
		doTest(getServerUrl() + "/group/test", "group.xml" , "group-post.properties", true, true);
	}
	
	@Test
	public void testDatasetResource() {
		doTest(getServerUrl() + "/group/test", "group.xml", null, false, false);
		doTest(getServerUrl() + "/group/test/data", "ecg_small.csv", null, true, true);
	}
	
	@Test
	public void testAnalysis() {
		doTest(getServerUrl() + "/group/test", "group.xml", null, false, false);
		doTest(getServerUrl() + "/group/test/data", "ecg_small.csv", null, false, false);
		doTest(getServerUrl() + "/engine/workflow/ecg.xml", null, "ecg-test.properties", false, false);
	}
	
//	@Test
	public void testUsers() {
		doTest(getServerUrl() + "/group/test", "group.xml", null, false, false);
		doTest(getServerUrl() + "/group/test/users", null, null, true, false);
	}
	
	@Test
	public void testIndex() throws ResourceException, IOException {
		ClientResource cr = new ClientResource(getServerUrl() + "/workflow");
		cr.get().write(System.out);
	}
	
	

	
}
