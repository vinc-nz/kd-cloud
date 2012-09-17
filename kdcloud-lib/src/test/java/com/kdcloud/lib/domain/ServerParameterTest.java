package com.kdcloud.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServerParameterTest {

	@Test
	public void test() {
		String uri = "/test/{param}";
		ServerParameter param = ServerParameter.getParamsFromUri(uri).iterator().next();
		assertEquals("param", param.getName());
		assertEquals("{xpath:test}", param.toReference("test").toString());
		assertEquals("x", param.toValue("x").toString());
	}
	
	@Test
	public void test3() {
		String expr = "/this/is[1]/an[last()-1]/xpath[@lang='eng']/expression";
		String uri = "/test/{xpath:" + expr + "}/test";
		ServerParameter param = ServerParameter.getParamsFromUri(uri).iterator().next();
		assertTrue(param.hasReference());
		assertEquals(expr, param.getReference());
	}

}
