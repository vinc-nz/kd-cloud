/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.engine.embedded.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeLoader;

@XmlRootElement(name="workflow")
@XmlAccessorType(XmlAccessType.NONE)
public class WorkflowDescription {
	
	@XmlElement(name="node")
	List<NodeDescription> nodes = new LinkedList<NodeDescription>();
	
	public Node[] getInstance(NodeLoader nodeLoader) throws IOException {
		Node[] workflow = new Node[nodes.size()];
		int i = 0;
		for (NodeDescription factory : nodes) {
			workflow[i] = factory.create(nodeLoader);
			i++;
		}
		return workflow;
	}
	

}
