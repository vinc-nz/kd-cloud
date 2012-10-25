package com.kdcloud.engine.embedded;

public interface NodeLoader {
	
	public Class<? extends Node> loadNode(String className) throws ClassNotFoundException;

}
