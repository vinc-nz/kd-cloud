package com.kdcloud.server.rest.application;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.Method;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.dev.LocalTaskQueue;
import com.google.appengine.api.taskqueue.dev.QueueStateInfo;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;

public class QueueTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalTaskQueueTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	// Run this test twice to demonstrate we're not leaking state across tests.
	// If we _are_ leaking state across tests we'll get an exception on the
	// second test because there will already be a task with the given name.
	private void doTest(Request request) throws InterruptedException {
		String url = request.getResourceRef().toString().replaceAll("\\?.*", "");
		TaskOptions opt = TaskOptions.Builder.withUrl(url);
		Form form = new Form(request.getEntity());
		for (String name : form.getNames())
			opt.param(name, form.getFirstValue(name));
		
		QueueFactory.getDefaultQueue().add(opt);
		// give the task time to execute if tasks are actually enabled (which
		// they
		// aren't, but that's part of the test)
		Thread.sleep(1000);
		LocalTaskQueue ltq = LocalTaskQueueTestConfig.getLocalTaskQueue();
		QueueStateInfo qsi = ltq.getQueueStateInfo().get(
				QueueFactory.getDefaultQueue().getQueueName());
		assertEquals(1, qsi.getTaskInfo().size());
	}

	@Test
	public void testTaskGetsScheduled1() throws InterruptedException {
		Request req = new Request(Method.POST, "/test");
		Form f = new Form();
		f.add("test", "test");
		req.setEntity(f.getWebRepresentation());
		doTest(req);
	}

}
