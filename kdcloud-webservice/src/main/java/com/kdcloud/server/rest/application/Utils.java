package com.kdcloud.server.rest.application;

import java.util.logging.Logger;

import org.restlet.Context;

import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.domain.ServerAction;
import com.kdcloud.server.domain.ServerMethod;
import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.domain.datastore.ModEntity;
import com.kdcloud.server.domain.datastore.Workflow;
import com.kdcloud.server.engine.embedded.EmbeddedEngine;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.rest.api.WorkflowResource;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.server.rest.api.UserDataResource;

public class Utils {

	public static void addStandardModalities(PersistenceContext pc, Logger logger) {
		ModalityDao modalityDao = pc.getModalityDao();
		Workflow workflow = EmbeddedEngine.getQRSWorkflow();
		pc.getWorkflowDao().save(workflow);
		logger.info("new workflow with id " + workflow.getId());

		ModEntity dataFeed = new ModEntity();
		dataFeed.setName("Data Feed");
		ServerAction createDataset = new ServerAction(UserDataResource.URI,
				ServerParameter.DATASET_ID.getName(), ServerMethod.PUT, false,
				10 * 60 * 1000);
		dataFeed.getServerCommands().add(createDataset);
		logger.info("modality " + dataFeed.getName() + " action " + createDataset);
		ServerAction uploadData = new ServerAction(DatasetResource.URI, null,
				ServerMethod.PUT, true, 10 * 60 * 1000);
		dataFeed.getServerCommands().add(uploadData);
		logger.info("modality " + dataFeed.getName() + " action " + uploadData);
		modalityDao.save(dataFeed);

		ModEntity singleAnalysis = new ModEntity();
		singleAnalysis.setName("Single Analysis");
		ServerAction analyze = new ServerAction(WorkflowResource.URI, null,
				ServerMethod.POST, true, 0);
		analyze = analyze.setParameter(ServerParameter.WORKFLOW_ID,
				Long.toString(workflow.getId()));
		analyze.addParameter(ServerParameter.USER_ID);
		singleAnalysis.getServerCommands().add(analyze);
		logger.info("modality " + singleAnalysis.getName() + " action " + analyze);
		modalityDao.save(singleAnalysis);

		ModEntity globalAnalysis = new ModEntity();
		globalAnalysis.setName("Global Analysis");
		ServerAction globalAnalyze = new ServerAction(
				GlobalAnalysisResource.URI, null, ServerMethod.POST, true, 0);
		globalAnalyze = globalAnalyze.setParameter(ServerParameter.WORKFLOW_ID,
				Long.toString(workflow.getId()));
		globalAnalysis.getServerCommands().add(globalAnalyze);
		logger.info("modality " + globalAnalysis.getName() + " action " + globalAnalyze);
		modalityDao.save(globalAnalysis);
	}

	public static void initDatabase(Context context) {
		PersistenceContextFactory pcf = (PersistenceContextFactory) context
				.getAttributes().get(PersistenceContextFactory.class.getName());
		PersistenceContext pc = pcf.get();
		cleanDatabase(pc);
		addStandardModalities(pc, context.getLogger());
	}

	public static void cleanDatabase(PersistenceContext pc) {
		pc.getModalityDao().deleteAll();
		// pc.getTaskDao().deleteAll();
		// pc.getDataTableDao().deleteAll();
	}

}
