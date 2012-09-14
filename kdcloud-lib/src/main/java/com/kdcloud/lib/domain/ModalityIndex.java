package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("modalities")
public class ModalityIndex implements Iterable<Modality>, Serializable {

	@XStreamImplicit
	List<Modality> list;

	public ModalityIndex(List<Modality> list) {
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