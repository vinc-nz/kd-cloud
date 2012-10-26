/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="modalities")
public class ModalityIndex implements Iterable<Modality>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
