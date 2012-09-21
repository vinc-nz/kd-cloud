package com.kdcloud.lib.domain;

import java.io.File;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.kdcloud.lib.domain.DataSpecification.Column;

public class JaxbTest {
	
	public static void main(String[] args) throws JAXBException {
		generateXml();
		readXml();
	}
	
	public static void readXml() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(JaxbTest.class
				.getPackage().getName());
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Modality m = (Modality) unmarshaller.unmarshal(new File("test.xml"));
		System.out.println(m.getName());
		System.out.println(m.getAction().getParams().get(0).getReferenceType());

	}
	
	public static void generateXml() throws JAXBException {
		Modality m = new Modality();
		m.inputSpecification = new DataSpecification();
		m.inputSpecification.columns = new LinkedList<DataSpecification.Column>();
		Column c = new Column();
		c.name = "test";
		m.inputSpecification.columns.add(c);
		JAXBContext context = JAXBContext.newInstance(JaxbTest.class.getPackage().getName());
		Marshaller mar = context.createMarshaller();
		mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		mar.marshal(m, System.out);
	}

}
