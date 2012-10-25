package com.kdcloud.engine.embedded;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="workflow")
public class WorkflowDescription {
	
	@XmlElement(name="node")
	List<NodeFactory> nodes = new LinkedList<NodeFactory>();
	
	public Node[] getInstance(NodeLoader nodeLoader) throws IOException {
		Node[] workflow = new Node[nodes.size()];
		int i = 0;
		for (NodeFactory factory : nodes) {
			workflow[i] = factory.create(nodeLoader);
			i++;
		}
		return workflow;
	}
	

}
