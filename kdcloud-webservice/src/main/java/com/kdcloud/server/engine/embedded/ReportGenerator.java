package com.kdcloud.server.engine.embedded;

import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import weka.core.Instance;
import weka.core.Instances;

public class ReportGenerator extends NodeAdapter {

	String view;
	Document viewSpec;
	DocumentBuilder db;
	BufferedInstances input;
	View output;

	public ReportGenerator(String xmlFilename) {
		this.view = xmlFilename;
	}
	
	public ReportGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setInput(PortObject input) throws WrongConnectionException {
		if (input instanceof BufferedInstances) {
			this.input = (BufferedInstances) input;
		} else {
			throw new WrongConnectionException();
		}
	}

	@Override
	public void configure(WorkerConfiguration config)
			throws WrongConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new WrongConfigurationException();
		}
		String filename = (String) config.get("view");
		if (filename != null)
			view = filename;
		try {
			loadXmlFromFile();
		} catch (Exception e) {
			throw new WrongConfigurationException("error loading view");
		}
		output = new View(viewSpec, db);
	}

	private void loadXmlFromFile() throws Exception {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(
				view);
		viewSpec = db.parse(stream);
	}

	@Override
	public PortObject getOutput() {
		return output;
	}

	@Override
	public void run(Logger logger) throws Exception {
		Instances data = input.getInstances();
		Document dom = output.getDom();
		Element docEle = dom.getDocumentElement();

		/* CONVERTING POINTS ELEMENTS */
		NodeList nl = docEle.getElementsByTagName("points");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element pointsElement = (Element) nl.item(i);
				Element parentElement = (Element) pointsElement.getParentNode();

				int xIndex = getIntValue(pointsElement, "xIndex");
				int yIndex = getIntValue(pointsElement, "yIndex");

				for (Instance instance : data) {
					Element xValue = dom.createElement("X");
					xValue.setTextContent(String.valueOf(instance.value(xIndex)));
					Element yValue = dom.createElement("Y");
					yValue.setTextContent(String.valueOf(instance.value(yIndex)));

					Element pointElement = dom.createElement("point");
					pointElement.appendChild(xValue);
					pointElement.appendChild(yValue);

					parentElement.appendChild(pointElement);
				}

				parentElement.removeChild(pointsElement);
			}
		}

		/* CONVERTING DATACOLS ELEMENTS */
		nl = docEle.getElementsByTagName("dataCol");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element dataColElement = (Element) nl.item(i);
				Element parentElement = (Element) dataColElement
						.getParentNode();

				int valueIndex = getIntValue(dataColElement, "valueIndex");

				Element colElement = dom.createElement("col");
				Element headerElement = dom.createElement("header");
				headerElement.setTextContent(getTextValue(dataColElement,
						"header"));
				colElement.appendChild(headerElement);

				for (Instance instance : data) {
					Element value = dom.createElement("value");
					value.setTextContent(String.valueOf(instance
							.value(valueIndex)));
					colElement.appendChild(value);
				}

				parentElement.removeChild(dataColElement);
				parentElement.appendChild(colElement);
			}
		}

		/* CONVERTING DATSERIES ELEMENTS */
		nl = docEle.getElementsByTagName("dataSeries");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element dataColElement = (Element) nl.item(i);
				Element parentElement = (Element) dataColElement
						.getParentNode();

				int valueIndex = getIntValue(dataColElement, "valueIndex");

				Element colElement = dom.createElement("series");
				Element headerElement = dom.createElement("title");
				headerElement.setTextContent(getTextValue(dataColElement,
						"title"));
				colElement.appendChild(headerElement);

				for (Instance instance : data) {
					Element value = dom.createElement("value");
					value.setTextContent(String.valueOf(instance
							.value(valueIndex)));
					colElement.appendChild(value);
				}

				parentElement.removeChild(dataColElement);
				parentElement.appendChild(colElement);
			}
		}

		/* CONVERTING DATACOLSERIES ELEMENTS */
		nl = docEle.getElementsByTagName("dataColSeries");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element dataColSeriesElement = (Element) nl.item(i);
				Element parentElement = (Element) dataColSeriesElement
						.getParentNode();

				int xIndex = getIntValue(dataColSeriesElement, "xIndex");
				int yIndex = getIntValue(dataColSeriesElement, "yIndex");

				Element titleElement = dom.createElement("title");
				titleElement.setTextContent(getTextValue(dataColSeriesElement,
						"title"));
				Element valueElement = dom.createElement("value");
				valueElement.setTextContent(String.valueOf(data.get(xIndex)
						.value(yIndex)));

				Element seriesElement = dom.createElement("series");
				seriesElement.appendChild(titleElement);
				seriesElement.appendChild(valueElement);

				parentElement.removeChild(dataColSeriesElement);
				parentElement.appendChild(seriesElement);
			}
		}
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get the text
	 * content i.e for <employee><name>John</name></employee> xml snippet if the
	 * Element points to employee node and tagName is 'name' I will return John
	 */
	public static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 */
	public static int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		try {
			return Integer.parseInt(getTextValue(ele, tagName));
		} catch (Exception e) {
			return 0;
		}
	}

}
