package com.kdcloud.server.engine.embedded;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class View implements PortObject {
	
	Document dom;
	

	public View(Document viewSpec, DocumentBuilder db) {
        Node originalRoot = viewSpec.getDocumentElement();

        dom = db.newDocument();
        Node copiedRoot = dom.importNode(originalRoot, true);
        dom.appendChild(copiedRoot);
	}
	
	
	public Document getDom() {
		return dom;
	}
	
}
