package com.kdcloud.server.rest.application;

import com.kdcloud.server.entity.User;

public interface UserNotifier {
	
	public void notify(User user);

}
