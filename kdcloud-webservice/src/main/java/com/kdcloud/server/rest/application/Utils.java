package com.kdcloud.server.rest.application;

import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.ServerAction;
import com.kdcloud.server.entity.ServerMethod;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.rest.api.AnalysisResource;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.server.rest.api.UserDataResource;

public class Utils {
	
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
