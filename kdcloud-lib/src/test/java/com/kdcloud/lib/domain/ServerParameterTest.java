package com.kdcloud.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServerParameterTest {

	@Test
	public void test() {
		String uri = "/test/{param}";
		ServerParameter param = ServerParameter.getParamsFromUri(uri).iterator().next();
		assertEquals("param", param.getName());
		assertEquals(new ServerParameter("input:param"), param.toInputReference());
		assertEquals(new ServerParameter("xpath:param"), param.toXPathReference());
		assertTrue(param.toInputReference().equals(param));
		assertFalse(param.toXPathReference().equals(param));
	}
	
	@Test
	public void test2() {
		String uri = "/test/{input:param}";
		ServerParameter param = ServerParameter.getParamsFromUri(uri).iterator().next();
		assertTrue(param.isInputReference());
		assertEquals("param", param.getName());
	}
	
	@Test
	public void test3() {
		String expr = "/this/is[1]/an[last()-1]/xpath[@lang='eng']/expression";
		String uri = "/test/{xpath:" + expr + "}/test";
		ServerParameter param = ServerParameter.getParamsFromUri(uri).iterator().next();
		assertTrue(param.isXPathReference());
		assertEquals(expr, param.getXPathExpression());
	}

}
