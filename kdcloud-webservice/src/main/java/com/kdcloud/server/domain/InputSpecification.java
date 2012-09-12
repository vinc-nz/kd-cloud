package com.kdcloud.server.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class InputSpecification implements Iterable<InputSource> {
	
	@XStreamImplicit
	List<InputSource> sources;
	
	public static Instances newInstances(String relationalName, int numAttributes) {
		ArrayList<Attribute> info = new ArrayList<Attribute>(numAttributes);
		for (int i = 0; i < numAttributes; i++) {
			String attrName = "attr" + Integer.toString(i);
			info.add(new Attribute(attrName));
		}
		return new Instances(relationalName, info, 0);
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
