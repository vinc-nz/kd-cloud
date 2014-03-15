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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;

import weka.core.Instances;

import com.kdcloud.lib.rest.ext.InstancesRepresentation;

@XmlType(name="")
@XmlAccessorType(XmlAccessType.NONE)
public class ServerAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required=true)
	String uri;
	
	@XmlElement(required=true)
	ServerMethod method;
	
	@XmlElement(name="parameter", required=false)
	Set<ServerParameter> postParams;
	
	@XmlElement(required=false)
	boolean repeat;

	@XmlElement(required=false)
	Trigger trigger;
	
	ArrayList<Parameter> postForm;
	
	static class Trigger implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@XmlAttribute
		int after;
	}
	
	public ServerAction() {
		this.postParams = new HashSet<ServerParameter>();
		this.postForm = new ArrayList<Parameter>();
	}


	public ServerAction(ServerAction serverAction) {
		this.method = serverAction.method;
		this.repeat = serverAction.repeat;
		this.trigger = serverAction.trigger;
		this.uri = serverAction.uri;
		this.postParams = serverAction.postParams;
		this.postForm = serverAction.postForm;
	}


	public ServerAction(String uri, ServerMethod method, boolean repeat, int sleepTime) {
		this.method = method;
		this.repeat = repeat;
		this.trigger = new Trigger();
		this.trigger.after = sleepTime;
		this.uri = uri;
		this.postParams = new HashSet<ServerParameter>();
		this.postForm = new ArrayList<Parameter>();
	}


	public ServerMethod getMethod() {
		return method;
	}

	public void setMethod(ServerMethod method) {
		this.method = method;
	}


	public String getUri() {
		return uri;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void waitTriggers() throws InterruptedException {
		if (trigger != null)
			Thread.sleep(trigger.after * 1000);
	}
	
	public List<ServerParameter> getParams() {
		List<ServerParameter> params = 
				new LinkedList<ServerParameter>();
		for (ServerParameter p : postParams) {
			if (!p.hasValue())
				params.add(p);
		}
		return params;
	}
	
	public boolean hasParameters() {
		return !getParams().isEmpty();
	}
	
	public boolean addParameter(String name) {
		return postParams.add(new ServerParameter(name));
	}
	
	public void setResourceIdentifier(String id) {
		uri = uri.replace("{id}", id);
	}

	public void setParameter(ServerParameter param, String value) {
		if (postParams.remove(param)) {
			postForm.add(new Parameter(param.getName(), value));
		}
	}
	
	public Representation getPostRepresentation() {
		for (ServerParameter p : postParams) {
			Parameter restletParameter = p.toRestletParameter();
			if (!postForm.contains(restletParameter) && p.hasValue())
				postForm.add(restletParameter);
		}
		return new Form(postForm).getWebRepresentation();
	}
	
	public Representation getPutRepresentation(Instances instances) {
		return new InstancesRepresentation(instances);
	}
	
	@Override
	public String toString() {
		String postString = " ( ";
		for (ServerParameter p : postParams) {
			postString = postString + p.getName() + " ";
		}
		postString = postString + ")";
		return method.toString() + ": " + uri + postString;
	}
	
}

