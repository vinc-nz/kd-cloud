package com.kdcloud.lib.domain;

import weka.core.Attribute;

public enum InputSource {
	HEARTBEAT,
	UNKNOWN;
	
	public String toString() {
		switch (this) {
		case HEARTBEAT:
			return "heartbeat";
		default:
			return "unknown";
		}
	};
	
	public Attribute toAttribute() {
		return new Attribute(toString());
	}

}
