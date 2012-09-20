package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
	InputSpecification inputSpecification;

	@XmlElement(name="action")
	List<ServerAction> serverCommands = new LinkedList<ServerAction>();;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public List<ServerAction> getServerCommands() {
		return serverCommands;
	}

	public InputSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(InputSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}

	public void setInputSources(List<InputSource> sources) {
		this.inputSpecification = new InputSpecification(sources);
	}

}
