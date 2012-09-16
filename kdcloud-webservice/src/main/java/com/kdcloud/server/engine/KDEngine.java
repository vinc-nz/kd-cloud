package com.kdcloud.server.engine;

import java.io.InputStream;



public interface KDEngine {

	public Worker getWorker(InputStream input);
	
}
