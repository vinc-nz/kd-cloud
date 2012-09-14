package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;

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
	public static final ServerParameter GROUP_ID = new ServerParameter("groupId");

	public static Set<ServerParameter> getParamsFromUri(String uri) {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		Matcher m = Pattern.compile("\\{.+?\\}").matcher(uri);
		while (m.find()) {
			String param = m.group().replaceAll("[\\{\\}]", "");
			params.add(new ServerParameter(param));
		}
		return params;
	}
	
	@XmlAttribute(name="value")
	private String mValue;

	public ServerParameter() {
		// TODO Auto-generated constructor stub
	}

	public ServerParameter(String value) {
		super();
		this.mValue = value;
	}

	public void setValue(String value) {
		this.mValue = value;
	}
	
	public String value() {
		return mValue;
	}

	public String getName() {
		if (isXPathReference())
			return null;
		return mValue.replaceAll(INPUT_PREFIX, "");
	}
	
	public boolean isReference() {
		return isXPathReference() || isInputReference();
	}
	
	public boolean isXPathReference() {
		return mValue.contains(XPATH_PREFIX);
	}
	
	public String getXPathExpression() {
		if (!isXPathReference())
			return null;
		return mValue.replaceAll(XPATH_PREFIX, "");
	}
	
	public boolean isInputReference() {
		return mValue.contains(INPUT_PREFIX);
	}
	
	public ServerParameter toInputReference() {
		String newValue;
		if (isXPathReference())
			newValue = mValue.replace(XPATH_PREFIX, INPUT_PREFIX);
		else if (isInputReference())
			newValue = mValue;
		else
			newValue = INPUT_PREFIX + mValue;
		return new ServerParameter(newValue);
	}
	
	public ServerParameter toXPathReference() {
		String newValue;
		if (isXPathReference())
			newValue = mValue;
		else if (isInputReference())
			newValue = mValue.replace(INPUT_PREFIX, XPATH_PREFIX);
		else
			newValue = XPATH_PREFIX + mValue;
		return new ServerParameter(newValue);
	}

	@Override
	public String toString() {
		return "{" + mValue + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerParameter) {
			ServerParameter other = (ServerParameter) obj;
			if (isXPathReference() && other.isXPathReference())
				return getXPathExpression().equals(other.getXPathExpression());
			if (isInputReference())
				return getName().equals(other.getName());
			return mValue.equals(other.mValue);
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
		return "\\{" + mValue + "\\}";
	}

}
