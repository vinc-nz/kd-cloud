package com.kdcloud.server.rest.application;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedirectsTest {

//	@Test
	public void test() {
		String sourcePath = Redirects.getRedirects().keySet().iterator().next();
		String destinationPath = Redirects.getRedirects().get(sourcePath);
		String sourceUrl = "http://myurl.com" + sourcePath;
		String destinationUrl = "http://myurl.com" + destinationPath;
		assertEquals(Redirects.getDestinationUrl(sourceUrl), destinationUrl);
		assertEquals(Redirects.getDestinationUrl(destinationUrl), destinationUrl);
		assertEquals(Redirects.getSourceUrl(destinationUrl), sourceUrl);
		assertEquals(Redirects.getSourceUrl(Redirects.getDestinationUrl(sourceUrl)), sourceUrl);
	}

}
