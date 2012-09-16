package com.kdcloud.server.engine.embedded;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kdcloud.server.engine.embedded.NodeFactory.InitParam;

@XmlRootElement(name="workflow")
public class WorkflowDescription {
	
	@XmlElement(name="node")
	List<NodeFactory> nodes = new LinkedList<NodeFactory>();
	
	public Node[] getInstance() throws Exception {
		Node[] workflow = new Node[nodes.size()];
		int i = 0;
		for (NodeFactory factory : nodes) {
			workflow[i] = factory.create();
			i++;
		}
		return workflow;
	}
	
	public static void main(String[] args) throws Exception {
		WorkflowDescription d = new WorkflowDescription();
		NodeFactory node = new NodeFactory(ReportGenerator.class);
		InitParam view = new InitParam();
		view.name = "view";
		view.value = "view.xml";
		node.parameters.add(view);
		d.nodes.add(node);
		JAXBContext context = JAXBContext.newInstance(WorkflowDescription.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(d, new File("ecg.xml"));
	}
	
	

}
