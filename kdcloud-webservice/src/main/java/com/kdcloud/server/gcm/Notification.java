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
