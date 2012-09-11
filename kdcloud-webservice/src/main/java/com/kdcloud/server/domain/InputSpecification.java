package com.kdcloud.server.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kdcloud.weka.core.Attribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class InputSpecification implements Iterable<InputSource> {
	
	@XStreamImplicit
	List<InputSource> sources;

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
