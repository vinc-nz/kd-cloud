package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.kdcloud.server.rest.api.DataRowResource;
import com.kdcloud.server.rest.api.DataTableResource;
import com.kdcloud.server.rest.api.ReportResource;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.rest.resource.DataRowServerResource;
import com.kdcloud.server.rest.resource.DataTableServerResource;
import com.kdcloud.server.rest.resource.ReportServerResource;
import com.kdcloud.server.rest.resource.SchedulerServerResource;

public class KDApplication extends Application {


	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
		
		Router router = new Router(getContext());

		router.attach(DataTableResource.URI, DataTableServerResource.class);
		router.attach(DataRowResource.URI, DataRowServerResource.class);
		router.attach(SchedulerResource.URI, SchedulerServerResource.class);
		router.attach(ReportResource.URI, ReportServerResource.class);

		return router;
	}
}
