package com.kdcloud.lib.domain;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class JaxbTest {
	
	public static void main(String[] args) {
		readXml();
	}
	
	public static void readXml() {
		try {
			JAXBContext context = JAXBContext.newInstance(JaxbTest.class.getPackage().getName());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			ModalityIndex index = (ModalityIndex) unmarshaller.unmarshal(new File("basic.xml"));
			System.out.println(index.asList().get(1).getServerCommands().get(0).getParams());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
