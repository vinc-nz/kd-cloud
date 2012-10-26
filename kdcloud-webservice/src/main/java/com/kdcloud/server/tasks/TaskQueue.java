package com.kdcloud.server.tasks;

import com.kdcloud.server.entity.Task;

public interface TaskQueue {
	
	
	public static final String WORKER_URI = "/_exec/{id}";	
	

	public void push(Task task);
}
