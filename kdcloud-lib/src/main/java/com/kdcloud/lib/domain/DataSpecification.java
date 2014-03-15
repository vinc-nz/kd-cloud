/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import weka.core.Attribute;
import weka.core.Instances;
@XmlType(name="")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class DataSpecification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAccessorType(XmlAccessType.FIELD)
	static class Column implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		DataType type;
		InputSource source;
	}
	
	public enum DataType {
		DOUBLE, TIMESTAMP, INTEGER
	}
	
	public enum InputSource {
		HEARTBEAT, CLOCK
	}
	
	@XmlElement(name="column")
	List<Column> columns;
	
	@XmlElement
	String view;
	
	public Instances newInstances(String relationalName) {
		return new Instances(relationalName, getAttrInfo(), 1000);
	}
	
	public boolean matchingSpecification(Instances instances) {
		return new Instances("", getAttrInfo(), 0).equalHeaders(instances);
	}
	
	public ArrayList<Attribute> getAttrInfo() {
		ArrayList<Attribute> info = new ArrayList<Attribute>(columns.size());
		for (Column c : columns) {
			info.add(new Attribute(c.name));
		}
		return info;
	}

	public String getView() {
		return view;
	}

	public static Instances newInstances(String name, int columns) {
		ArrayList<Attribute> info = new ArrayList<Attribute>(columns);
		for (int i = 0; i < columns; i++) {
			info.add(new Attribute("attr" + i));
		}
		return new Instances(name, info, 0);
	}

	public List<InputSource> sources() {
		LinkedList<InputSource> list = new LinkedList<DataSpecification.InputSource>();
		for (Column c : columns)
			list.add(c.source);
		return list;
	}

}
