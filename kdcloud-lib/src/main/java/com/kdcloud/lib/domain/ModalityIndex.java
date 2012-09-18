package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class ModalityIndex implements Iterable<Modality>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
