package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kdcloud.weka.core.Attribute;

public class ServerAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String uri;
	
	String outputLabel;
	
	ServerMethod method;
	
	ArrayList<Attribute> dataSpec;
	
	boolean repeat;
	
	long sleepTime;
	
	public ServerAction() {
		// TODO Auto-generated constructor stub
	}


	public ServerAction(String uri, String outputLabel, ServerMethod method,
			boolean repeat, long sleepTime) {
		super();
		this.uri = uri;
		this.outputLabel = outputLabel;
		this.method = method;
		this.repeat = repeat;
		this.sleepTime = sleepTime;
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

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(String outputLabel) {
		this.outputLabel = outputLabel;
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
	
	public ArrayList<Attribute> getDataSpec() {
		return dataSpec;
	}

	public void setDataSpec(ArrayList<Attribute> dataSpec) {
		this.dataSpec = dataSpec;
	}


	public List<ServerParameter> getParams() {
		return ServerParameter.getParamsFromUri(uri);
	}
	
	public boolean hasParameters() {
		return getParams().isEmpty();
	}

	public ServerAction setParameter(ServerParameter param, String value) {
		String newUri = uri.replaceAll(param.getPattern(), value);
		return new ServerAction(newUri, outputLabel, method, repeat, sleepTime);
	}
	
}
