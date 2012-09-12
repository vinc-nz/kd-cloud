package com.kdcloud.server.tasks;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.kdcloud.server.domain.datastore.Task;



public class GAETaskQueue implements TaskQueue {
	

	@Override
	public void push(Task task) {
		
		Queue queue = QueueFactory.getDefaultQueue();
		String uri = TaskQueue.WORKER_URI + Long.toString(task.getId());
	    TaskOptions opt = TaskOptions.Builder.withUrl(uri);
	    queue.add(opt);
	}

}
