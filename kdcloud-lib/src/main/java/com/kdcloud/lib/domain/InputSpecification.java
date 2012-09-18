package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import weka.core.Attribute;
import weka.core.Instances;

public class InputSpecification implements Iterable<InputSource>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@XmlElement(name="source")
	List<InputSource> sources;
	
	public static Instances newInstances(String relationalName, int numAttributes) {
		ArrayList<Attribute> info = new ArrayList<Attribute>(numAttributes);
		for (int i = 0; i < numAttributes; i++) {
			String attrName = "attr" + Integer.toString(i);
			info.add(new Attribute(attrName));
		}
		return new Instances(relationalName, info, 0);
	}
	
	public InputSpecification() {
		// TODO Auto-generated constructor stub
	}

	public InputSpecification(List<InputSource> sources) {
		super();
		this.sources = sources;
	}

	@Override
	public Iterator<InputSource> iterator() {
		return sources.iterator();
	}
	
	public ArrayList<Attribute> attrInfo() {
		ArrayList<Attribute> info = new ArrayList<Attribute>(sources.size());
		for (InputSource s : sources) {
			info.add(s.toAttribute());
		}
		return info;
	}

}
