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
package com.kdcloud.lib.domain;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Form;

import com.kdcloud.lib.domain.ServerAction;
import com.kdcloud.lib.domain.ServerMethod;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.EngineResource;

public class ServerActionTest {

	@Test
	public void test() {
		ServerAction action = new ServerAction(EngineResource.URI,
				ServerMethod.GET, false, 0);
		action.setResourceIdentifier("1");
		Assert.assertEquals("/engine/workflow/1", action.uri);
		String param ="userId";
		action.addParameter(param);
		ServerParameter p = action.getParams().iterator().next();
		action.setParameter(p, "test");
		Assert.assertEquals("test",
				new Form(action.postForm).getFirstValue(param));
	}
}
