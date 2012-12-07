/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.engine.embedded;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class NodeDescription {
	
	public static final String NODE_PACKAGE = "com.kdcloud.engine.embedded.node";

	public NodeDescription() {
		// TODO Auto-generated constructor stub
	}

	public NodeDescription(Class<? extends Node> type) {
		super();
		this.type = type.getSimpleName();
	}
	
	@XmlElement
	String type;

	@XmlElement(name = "parameter")
	List<InitParam> parameters = new LinkedList<NodeDescription.InitParam>();

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
