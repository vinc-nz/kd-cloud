package com.kdcloud.lib.domain;

import org.junit.Assert;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/*
 * used by TestRestletSerialization
 */
public class TestResource extends ServerResource {
	
	@Get
	public Index getIndex() {
		return new Index();
	}
	
	@Put
	public void putIndex(Index i) {
		Assert.assertNotNull(i);
	}
	
}
