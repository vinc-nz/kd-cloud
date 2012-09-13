package com.kdcloud.lib.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.kdcloud.lib.rest.api.DatasetResource;
import com.kdcloud.lib.rest.api.GlobalAnalysisResource;
import com.kdcloud.lib.rest.api.UserDataResource;
import com.kdcloud.lib.rest.api.WorkflowResource;
import com.thoughtworks.xstream.XStream;

public class XstreamTest {
	
	public static void main(String[] args) {
//		generateXml();
		readXml();
	}
	
	public static void readXml() {
		XStream stream = new XStream();
		stream.processAnnotations(ModalityIndex.class);
		ModalityIndex index = (ModalityIndex) stream.fromXML(new File("basic.xml"));
		for (Modality modality : index) {
			System.out.println(modality.getName());
		}
	}

	
	public static void generateXml() {
		ArrayList<Modality> list = new ArrayList<Modality>();
		
		Modality dataFeed = new Modality("Data Feed");
		dataFeed.setInputSources(Arrays.asList(InputSource.HEARTBEAT));
		ServerAction createDataset = new ServerAction(UserDataResource.URI,
				ServerParameter.DATASET_ID.getName(), ServerMethod.PUT, false,
				10 * 60 * 1000);
		dataFeed.getServerCommands().add(createDataset);
		ServerAction uploadData = new ServerAction(DatasetResource.URI, null,
				ServerMethod.PUT, true, 10 * 60 * 1000);
		dataFeed.getServerCommands().add(uploadData);
		list.add(dataFeed);

		Modality singleAnalysis = new Modality("Single Analysis");
		ServerAction analyze = new ServerAction(WorkflowResource.URI, null,
				ServerMethod.POST, true, 0);
		analyze = analyze.setParameter(ServerParameter.WORKFLOW_ID, "test");
		analyze.addParameter(ServerParameter.USER_ID.toInputReference());
		singleAnalysis.getServerCommands().add(analyze);
		list.add(singleAnalysis);

		Modality globalAnalysis = new Modality("Global Analysis");
		ServerAction globalAnalyze = new ServerAction(
				GlobalAnalysisResource.URI, null, ServerMethod.POST, true, 0);
		globalAnalyze = globalAnalyze.setParameter(ServerParameter.WORKFLOW_ID,
				"test");
		globalAnalysis.getServerCommands().add(globalAnalyze);
		list.add(globalAnalysis);
		
		ModalityIndex index = new ModalityIndex(list);
		XStream stream = new XStream();
		stream.autodetectAnnotations(true);
		try {
			FileOutputStream out = new FileOutputStream("basic.xml");
			stream.toXML(index, out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
