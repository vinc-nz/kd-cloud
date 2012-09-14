package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="modalities")
public class ModalityIndex implements Iterable<Modality>, Serializable {

	@XmlElement(name="modality")
	List<Modality> list;
	
	public ModalityIndex() {
		// TODO Auto-generated constructor stub
	}

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
