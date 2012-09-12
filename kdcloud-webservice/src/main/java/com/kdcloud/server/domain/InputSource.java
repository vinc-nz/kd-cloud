package com.kdcloud.server.domain;

import weka.core.Attribute;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("input-source")
public enum InputSource {
	HEARTBEAT;
	
	public String toString() {
		return "heartbeat";
	};
	
	public Attribute toAttribute() {
		return new Attribute(toString());
	}

}
