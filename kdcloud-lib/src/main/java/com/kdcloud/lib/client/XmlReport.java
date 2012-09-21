package com.kdcloud.lib.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import weka.core.Instance;
import weka.core.Instances;

public class XmlReport {
	
	public static void mergeWithData(Document dom, Instances data) {
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
	private static String getTextValue(Element ele, String tagName) {
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
	private static int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		try {
			return Integer.parseInt(getTextValue(ele, tagName));
		} catch (Exception e) {
			return 0;
		}
	}

}
