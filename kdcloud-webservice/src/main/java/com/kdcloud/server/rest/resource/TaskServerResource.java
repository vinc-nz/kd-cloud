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

import org.restlet.Application;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;

import weka.core.Instances;

import com.kdcloud.lib.rest.api.TaskResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.Task;

public class TaskServerResource extends KDServerResource implements TaskResource {
	
	private Task task;
	
	public TaskServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	TaskServerResource(Application application, Task task) {
		super(application, null);
		this.task = task;
	}
	
	@Override
	public Representation handle() {
		String taskId = getResourceIdentifier();
		Task task = taskDao.findById(new Long(taskId));
		if (task == null)
			return notFound();
		if (!task.getApplicant().equals(user))
			return forbidden();
		return super.handle();
	}

	@Override
	public Representation retriveOutput() {
		Instances output = task.getOutput();
		if (output != null && !output.isEmpty())
			return new InstancesRepresentation(MediaType.TEXT_CSV, output);
		return null;
	}

}
