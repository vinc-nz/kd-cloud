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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
@XmlRootElement(name="modality")
@XmlAccessorType(XmlAccessType.NONE)
public class ModalitySpecification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required=false)
	DataSpecification inputSpecification;
	
	@XmlElement(name="init-action", required=false)
	ServerAction initAction;

	@XmlElement(name="action")
	ServerAction action;
	
	@XmlElement(required=false)
	DataSpecification outputSpecification;
	

	public DataSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(DataSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}

	public ServerAction getInitAction() {
		return initAction;
	}

	public void setInitAction(ServerAction initAction) {
		this.initAction = initAction;
	}

	public ServerAction getAction() {
		return action;
	}

	public void setAction(ServerAction action) {
		this.action = action;
	}

	public DataSpecification getOutputSpecification() {
		return outputSpecification;
	}

	public void setOutputSpecification(DataSpecification outputSpecification) {
		this.outputSpecification = outputSpecification;
	}

}
