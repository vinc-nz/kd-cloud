package com.kdcloud.server.tasks;

import com.kdcloud.server.domain.datastore.Task;

public interface TaskQueue {
	
	
	public static final String WORKER_URI = "/_exec/";	
	

	public void push(Task task);
}
