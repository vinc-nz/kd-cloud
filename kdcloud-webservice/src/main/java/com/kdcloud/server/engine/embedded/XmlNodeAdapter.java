package com.kdcloud.server.engine.embedded;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlNodeAdapter extends XmlAdapter<Object, Node> {

	@Override
	public Object marshal(Node v) throws Exception {
		return v;
	}

	@Override
	public Node unmarshal(Object v) throws Exception {
		return (Node) v;
	}

}
