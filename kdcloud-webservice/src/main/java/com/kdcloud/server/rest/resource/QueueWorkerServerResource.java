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
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import weka.core.Instances;

import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.gcm.Notification;

public class QueueWorkerServerResource extends WorkerServerResource {
	
	private Task task;

	public QueueWorkerServerResource() {
		super();
	}

	QueueWorkerServerResource(Application application, Task task) {
		super(application, null);
		this.task = task;
	}
	
	@Override
	public Representation handle() {
		String id = getResourceIdentifier();
		task = taskDao.findById(new Long(id));
		if (task == null)
			return notFound();
		return super.handle();
	}
	
	@Post
	public void pullTask(Form form) {
		getLogger().info("ready to work on data");

		try {
			Instances output = execute(form, task.getStream());

			getLogger().info("work done");

			task.setOutput(output);
			taskDao.save(task);
			notifyApplicant(task);
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void notifyApplicant(Task task) {
		User user = task.getApplicant();
		if (user.getDevices().size() > 0)
			try {
				Notification.notify(task, user);
				getLogger().info("user has been notified");
			} catch (IOException e) {
				getLogger().info("unable to notify user");
			}
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
