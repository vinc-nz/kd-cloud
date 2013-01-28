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
import org.restlet.resource.ResourceException;

import com.kdcloud.server.rest.application.RestletTestCase;

public class ServerResourceTest extends RestletTestCase {

	@Test
	public void testGroupResource() {
		doFullTest("/group/test", "group.xml" , "group-post.properties", true, true);
	}
	
	@Test
	public void testDatasetResource() {
		doFullTest("/group/london-marathon/data", "ecg_small.csv", null, true, true);
	}
	
	@Test
	public void testAnalysis() {
		doPut("/group/london-marathon/data", "ecg_small.csv");
		doPost("/engine/workflow/ecg.xml", "ecg-test.properties");
	}
	
//	@Test
	public void testUsers() {
		doPut("/group/test", "group.xml");
		doGet("/group/test/contributors");
	}
	
	@Test
	public void testIndex() throws ResourceException, IOException {
		doGet("/engine/workflow");
		doGet("/modality");
		doGet("/view");
		doGet("/engine/plugin");
	}
	
}
