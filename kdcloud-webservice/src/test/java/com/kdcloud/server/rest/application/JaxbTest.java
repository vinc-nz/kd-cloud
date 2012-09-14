package com.kdcloud.server.rest.application;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.restlet.ext.jaxb.JaxbRepresentation;

import com.kdcloud.lib.domain.InputSource;
import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.domain.ServerAction;
import com.kdcloud.lib.domain.ServerMethod;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.DatasetResource;
import com.kdcloud.lib.rest.api.GlobalAnalysisResource;
import com.kdcloud.lib.rest.api.UserDataResource;
import com.kdcloud.lib.rest.api.WorkflowResource;

public class JaxbTest {
	
	public static void main(String[] args) {
		generateXml();
//		readXml();
	}
	
	public static void readXml() {
		try {
			JAXBContext context = JAXBContext.newInstance(ModalityIndex.class.getPackage().getName());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			ModalityIndex index = (ModalityIndex) unmarshaller.unmarshal(new File("basic.xml"));
			System.out.println(index.asList().iterator().next().getServerCommands().size());
		} catch (Exception e) {
			e.printStackTrace();
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
		
		try {
			JaxbRepresentation<ModalityIndex> rep = new JaxbRepresentation<ModalityIndex>(index);
			org.restlet.ext.jaxb.internal.Marshaller<ModalityIndex> m = new org.restlet.ext.jaxb.internal.Marshaller<ModalityIndex>(
					rep, "com.kdcloud.lib.domain", JaxbTest.class
							.getClassLoader());
			m.marshal(index, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
//		XStream stream = new XStream();
//		stream.autodetectAnnotations(true);
//		try {
//			stream.toXML(index, out);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
