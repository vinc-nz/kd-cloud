package com.kdcloud.lib.domain;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

public class JaxbTest {

	@Test
	public void readXml() {
		try {
			JAXBContext context = JAXBContext.newInstance(JaxbTest.class
					.getPackage().getName());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream stream = getClass().getClassLoader()
					.getResourceAsStream("modalities.xml");
			Object result = unmarshaller.unmarshal(stream);
			Assert.assertNotNull(result);
		} catch (JAXBException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	// @Test
	// public static void generateXml() throws JAXBException {
	// Modality m = new Modality();
	// m.inputSpecification = new DataSpecification();
	// m.inputSpecification.columns = new
	// LinkedList<DataSpecification.Column>();
	// Column c = new Column();
	// c.name = "test";
	// m.inputSpecification.columns.add(c);
	// JAXBContext context =
	// JAXBContext.newInstance(JaxbTest.class.getPackage().getName());
	// Marshaller mar = context.createMarshaller();
	// mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// mar.marshal(m, System.out);
	// }

}
