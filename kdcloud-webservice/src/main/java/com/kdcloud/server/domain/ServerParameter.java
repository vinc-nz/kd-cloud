package com.kdcloud.server.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("server-parameter")
public class ServerParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final ServerParameter DATASET_ID = new ServerParameter(
			"datasetId");
	public static final ServerParameter USER_ID = new ServerParameter("userId");
	public static final ServerParameter TASK_ID = new ServerParameter("taskId");
	public static final ServerParameter MODALITY_ID = new ServerParameter(
			"modalityId");
	public static final ServerParameter WORKFLOW_ID = new ServerParameter(
			"workflowId");

	public static Set<ServerParameter> getParamsFromUri(String uri) {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		Matcher m = Pattern.compile("\\{\\w+\\}").matcher(uri);
		while (m.find()) {
			String param = m.group().replaceAll("[\\{\\}]", "");
			params.add(new ServerParameter(param));
		}
		return params;
	}

	@XStreamAsAttribute
	private String name;

	public ServerParameter() {
		// TODO Auto-generated constructor stub
	}

	public ServerParameter(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "{" + name + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerParameter)
			return ((ServerParameter) obj).name.equals(name);
		return false;
	}

	public int hashCode() {
		return name.hashCode();
	};

	String getPattern() {
		return "\\{" + name + "\\}";
	}

}
