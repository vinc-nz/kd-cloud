package com.kdcloud.lib.domain;

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
	
	private static final String INPUT_PREFIX = "input:";
	private static final String XPATH_PREFIX = "xpath:";

	public static final ServerParameter DATASET_ID = new ServerParameter("datasetId");
	public static final ServerParameter USER_ID = new ServerParameter("userId");
	public static final ServerParameter TASK_ID = new ServerParameter("taskId");
	public static final ServerParameter MODALITY_ID = new ServerParameter("modalityId");
	public static final ServerParameter WORKFLOW_ID = new ServerParameter("workflowId");

	public static Set<ServerParameter> getParamsFromUri(String uri) {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		Matcher m = Pattern.compile("\\{.+?\\}").matcher(uri);
		while (m.find()) {
			String param = m.group().replaceAll("[\\{\\}]", "");
			params.add(new ServerParameter(param));
		}
		return params;
	}
	
	@XStreamAsAttribute
	private String value;

	public ServerParameter() {
		// TODO Auto-generated constructor stub
	}

	public ServerParameter(String value) {
		super();
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public String getName() {
		if (isXPathReference())
			return null;
		return value.replaceAll(INPUT_PREFIX, "");
	}
	
	public boolean isReference() {
		return isXPathReference() || isInputReference();
	}
	
	public boolean isXPathReference() {
		return value.contains(XPATH_PREFIX);
	}
	
	public String getXPathExpression() {
		if (!isXPathReference())
			return null;
		return value.replaceAll(XPATH_PREFIX, "");
	}
	
	public boolean isInputReference() {
		return value.contains(INPUT_PREFIX);
	}
	
	public ServerParameter toInputReference() {
		String newValue;
		if (isXPathReference())
			newValue = value.replace(XPATH_PREFIX, INPUT_PREFIX);
		else if (isInputReference())
			newValue = value;
		else
			newValue = INPUT_PREFIX + value;
		return new ServerParameter(newValue);
	}
	
	public ServerParameter toXPathReference() {
		String newValue;
		if (isXPathReference())
			newValue = value;
		else if (isInputReference())
			newValue = value.replace(INPUT_PREFIX, XPATH_PREFIX);
		else
			newValue = XPATH_PREFIX + value;
		return new ServerParameter(newValue);
	}

	@Override
	public String toString() {
		return "{" + value + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerParameter) {
			ServerParameter other = (ServerParameter) obj;
			if (isXPathReference() && other.isXPathReference())
				return getXPathExpression().equals(other.getXPathExpression());
			if (isInputReference())
				return getName().equals(other.getName());
			return value.equals(other.value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (isXPathReference())
			return getXPathExpression().hashCode();
		return getName().hashCode();
	};

	String getPattern() {
		return "\\{" + value + "\\}";
	}

}
