package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;

import weka.core.Instances;

import com.kdcloud.lib.rest.ext.InstancesRepresentation;

@XmlAccessorType(XmlAccessType.NONE)
public class ServerAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	String uri;
	
	@XmlElement
	ServerMethod method;
	
	@XmlElement(name="postParameter")
	Set<ServerParameter> postParams;
	
	ArrayList<Parameter> postForm;
	
	boolean repeat;
	
	long sleepTime;
	
	public ServerAction() {
		// TODO Auto-generated constructor stub
	}


	public ServerAction(String uri, ServerMethod method,
			boolean repeat, long sleepTime) {
		super();
		this.uri = uri;
		this.method = method;
		this.repeat = repeat;
		this.sleepTime = sleepTime;
		this.postParams = new HashSet<ServerParameter>();
		this.postForm = new ArrayList<Parameter>();
	}


	ServerAction(ServerAction serverAction, String newUri, ArrayList<Parameter> newPostForm) {
		this.method = serverAction.method;
		this.repeat = serverAction.repeat;
		this.sleepTime = serverAction.sleepTime;
		this.uri = newUri;
		this.postParams = serverAction.postParams;
		this.postForm = newPostForm;
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

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public List<ServerParameter> getParams() {
		List<ServerParameter> params = 
				new LinkedList<ServerParameter>();
		params.addAll(ServerParameter.getParamsFromUri(uri));
		for (ServerParameter p : postParams) {
			if (!p.hasValue())
				params.add(p);
		}
		return params;
	}
	
	public boolean hasParameters() {
		return !getParams().isEmpty();
	}
	
	public boolean addParameter(ServerParameter param) {
		return postParams.add(param);
	}

	public ServerAction setParameter(ServerParameter param, String value) {
		String newUri = uri;
		ArrayList<Parameter> newPostForm = postForm;
		if (ServerParameter.getParamsFromUri(uri).contains(param)) {
			newUri = uri.replaceAll(param.getPattern(), value);
		} else if (postParams.contains(param)) {
			newPostForm = new ArrayList<Parameter>(postForm);
			Parameter postParam = new Parameter(param.getName(), value);
			newPostForm.add(postParam);
		}
		return new ServerAction(this, newUri, newPostForm);
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

