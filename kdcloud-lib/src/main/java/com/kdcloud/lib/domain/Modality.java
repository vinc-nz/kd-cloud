package com.kdcloud.lib.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="modality")
@XmlAccessorType(XmlAccessType.NONE)
public class Modality implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int id;
	
	@XmlElement
	String name;
	
	@XmlElement
	DataSpecification inputSpecification;
	
	@XmlElement(name="init-action")
	ServerAction initAction;

	@XmlElement(name="action")
	ServerAction action;
	
	@XmlElement
	DataSpecification outputSpecification;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

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

	public void setName(String name) {
		this.name = name;
	}
	
	

}
