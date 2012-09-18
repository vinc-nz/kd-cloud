package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.restlet.data.Parameter;

public class ServerParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String REFERENCE_PREFIX = "xpath:";
	
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
	
	public static String newReference(String reference) {
		if (reference.contains(REFERENCE_PREFIX))
			return reference;
		return REFERENCE_PREFIX + reference;
	}
	
	private final String name;
	private final String value;
	private final String reference;

	public ServerParameter() {
		this(null, null, null);
	}

	public ServerParameter(String representation) {
		this.value = null;
		if (representation.contains(REFERENCE_PREFIX)) {
			this.name = null;
			this.reference = representation.replace(REFERENCE_PREFIX, "");
		}
		else {
			this.name = representation;
			this.reference = null;
		}
	}
	
	public ServerParameter(String name, String value, String reference) {
		super();
		this.name = name;
		this.value = value;
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getReference() {
		return reference;
	}

	public boolean hasReference() {
		return reference != null;
	}
	
	public boolean hasValue() {
		return value != null;
	}
	
	public ServerParameter toReference(String reference) {
		return new ServerParameter(name, null, reference);
	}
	
	public ServerParameter toValue(String value) {
		return new ServerParameter(name, value, null);
	}
	
	public Parameter toRestletParameter() {
		return new Parameter(name, value);
	}

	@Override
	public String toString() {
		if (value != null)
			return value;
		String repr = (reference != null ? REFERENCE_PREFIX + reference : name);
		return "{" + repr + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerParameter) {
			ServerParameter other = (ServerParameter) obj;
			if (this.name == null) {
				if (other.name == null)
					return this.reference.equals(other.reference);
				return false;
			}
			return this.name.equals(other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (name != null)
			return name.hashCode();
		return reference.hashCode();
	};

	String getPattern() {
		return "\\{" + name + "\\}";
	}

}
