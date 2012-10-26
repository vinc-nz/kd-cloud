/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
