package com.kdcloud.engine.embedded;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class NodeFactory {
	
	public static final String NODE_PACKAGE = "com.kdcloud.engine.embedded.node";

	public NodeFactory() {
		// TODO Auto-generated constructor stub
	}

	public NodeFactory(Class<? extends Node> type) {
		super();
		this.type = type.getSimpleName();
	}
	
	@XmlElement
	String type;

	@XmlElement(name = "parameter")
	List<InitParam> parameters = new LinkedList<NodeFactory.InitParam>();

	static class InitParam {

		public String name;
		public String value;
	}

	public Node create(NodeLoader nodeLoader) throws IOException {
		try {
			String className = NODE_PACKAGE + "." + type;
			Class<? extends Node> clazz = nodeLoader.loadNode(className);
			Node node = clazz.newInstance();
			for (InitParam p : parameters) {
				String setter = "set" + Character.toUpperCase(p.name.charAt(0))
						+ p.name.substring(1);
				clazz.getMethod(setter, String.class).invoke(node, p.value);
			}
			return node;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("error creating node " + type);
		}
	}

}
