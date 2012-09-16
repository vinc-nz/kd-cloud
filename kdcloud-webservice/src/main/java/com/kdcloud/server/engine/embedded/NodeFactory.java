package com.kdcloud.server.engine.embedded;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class NodeFactory {
	
	public NodeFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public NodeFactory(Class<? extends Node> type) {
		super();
		this.type = type.getSimpleName();
	}

	@XmlElement
	String type;

	@XmlElement(name="parameter")
	List<InitParam> parameters = new LinkedList<NodeFactory.InitParam>();

	static class InitParam {

		public String name;
		public String value;
	}

	public Node create() throws Exception {
		String packageName = Node.class.getPackage().getName();
		String className = packageName + "." + type;
		Class<? extends Node> clazz = Class.forName(className).asSubclass(
				Node.class);
		Node node = clazz.newInstance();
		for (InitParam p : parameters) {
			clazz.getDeclaredField(p.name).set(node, p.value);
		}
		return node;
	}

}
