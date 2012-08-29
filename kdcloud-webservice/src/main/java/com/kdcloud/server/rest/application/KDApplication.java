package com.kdcloud.server.rest.application;

import javax.jdo.PersistenceManager;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.ServerAction;
import com.kdcloud.server.entity.ServerMethod;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.jdo.GaeModalityDao;
import com.kdcloud.server.jdo.PMF;
import com.kdcloud.server.rest.api.AnalysisResource;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.DeviceResource;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.server.rest.api.GlobalDataResource;
import com.kdcloud.server.rest.api.ModalitiesResource;
import com.kdcloud.server.rest.api.ReportResource;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.rest.api.UserDataResource;
import com.kdcloud.server.rest.api.UserDetailsResource;
import com.kdcloud.server.rest.resource.AnalysisServerResource;
import com.kdcloud.server.rest.resource.DatasetServerResource;
import com.kdcloud.server.rest.resource.DeviceServerResource;
import com.kdcloud.server.rest.resource.GlobalAnalysisServerResource;
import com.kdcloud.server.rest.resource.GlobalDataServerResource;
import com.kdcloud.server.rest.resource.ModalitiesServerResource;
import com.kdcloud.server.rest.resource.ReportServerResource;
import com.kdcloud.server.rest.resource.SchedulerServerResource;
import com.kdcloud.server.rest.resource.UserDataServerResource;
import com.kdcloud.server.rest.resource.UserDetailsServerResource;

public class KDApplication extends Application {

	public KDApplication() {
		super();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ModalityDao modalityDao = new GaeModalityDao(pm);
		if (modalityDao.getAll().isEmpty()) {
			addStandardModalities(modalityDao);
		}
	}

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach(UserDataResource.URI, UserDataServerResource.class);
		router.attach(DatasetResource.URI, DatasetServerResource.class);
		router.attach(AnalysisResource.URI, AnalysisServerResource.class);
		router.attach(SchedulerResource.URI, SchedulerServerResource.class);
		router.attach(ReportResource.URI, ReportServerResource.class);
		router.attach(DeviceResource.URI, DeviceServerResource.class);
		router.attach(UserDetailsResource.URI, UserDetailsServerResource.class);
		router.attach(ModalitiesResource.URI, ModalitiesServerResource.class);
		router.attach(GlobalAnalysisResource.URI,
				GlobalAnalysisServerResource.class);
		router.attach(GlobalDataResource.URI, GlobalDataServerResource.class);

		return router;
	}
	
	public static void addStandardModalities(ModalityDao modalityDao) {
		Modality dataFeed = new Modality();
		dataFeed.setName("Data Feed");
		dataFeed.getSensors().add("ecg");
		ServerAction createDataset = new ServerAction(UserDataResource.URI,
				ServerParameter.DATASET_ID.getName(), ServerMethod.GET, false,
				10 * 60 * 1000);
		dataFeed.getServerCommands().add(createDataset);
		ServerAction uploadData = new ServerAction(DatasetResource.URI, null,
				ServerMethod.PUT, true, 10 * 60 * 1000);
		dataFeed.getServerCommands().add(uploadData);
		modalityDao.save(dataFeed);

		Modality singleAnalysis = new Modality();
		singleAnalysis.setName("Single Analysis");
		ServerAction analyze = new ServerAction(AnalysisResource.URI, null,
				ServerMethod.GET, false, 0);
		singleAnalysis.getServerCommands().add(analyze);
		modalityDao.save(singleAnalysis);

		Modality globalAnalysis = new Modality();
		globalAnalysis.setName("Global Analysis");
		ServerAction globalAnalyze = new ServerAction(
				GlobalAnalysisResource.URI, null, ServerMethod.GET, false, 0);
		globalAnalysis.getServerCommands().add(globalAnalyze);
		modalityDao.save(globalAnalysis);
	}
}
