package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.LinkedList;

public class Action implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String serverCommand;
	
	LinkedList<String> params = new LinkedList<String>();
	
	String method;
	
	boolean repeat;
	
	long sleepTime;
	
	public Action() {
		// TODO Auto-generated constructor stub
	}

	public Action(String serverCommand, String method, boolean repeat,
			long sleepTime) {
		super();
		this.serverCommand = serverCommand;
//		Pattern p = Pattern.compile("\\{\\w+\\}");
		this.method = method;
		this.repeat = repeat;
		this.sleepTime = sleepTime;
	}


	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getServerCommand() {
		return serverCommand;
	}

	public void setServerCommand(String serverCommand) {
		this.serverCommand = serverCommand;
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
	
	
}
