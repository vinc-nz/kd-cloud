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
package com.kdcloud.server.gcm;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;

public abstract class Notification {
	
	private static final String GOOGLE_API_KEY = "AIzaSyCdog7MGmFI9XdMUR2OKDhWsioqkiiFzB4";
	
	public static final void notify(Task task, User user) throws IOException {
		gcmNotify(task, user);
	}
	
	
	private static final void gcmNotify(Task task, User user) throws IOException {
		Sender sender = new Sender(GOOGLE_API_KEY);
		String id = Long.toString(task.getId());
		Message message = new Message.Builder().addData("id", id).build();
		for (String regId : user.getDevices())
				sender.sendNoRetry(message, regId);
	}

}
