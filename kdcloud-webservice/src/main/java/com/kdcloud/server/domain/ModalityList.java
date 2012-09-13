package com.kdcloud.server.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("modalities")
public class ModalityList implements Iterable<Modality>, Serializable {

	@XStreamImplicit
	List<Modality> list;

	public ModalityList(List<Modality> list) {
		super();
		this.list = list;
	}

	@Override
	public Iterator<Modality> iterator() {
		return list.iterator();
	}

	public List<Modality> asList() {
		return list;
	}

}
