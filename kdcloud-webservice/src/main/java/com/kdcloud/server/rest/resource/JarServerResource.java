package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.jar.JarInputStream;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public class JarServerResource extends KDServerResource {
	
	public static final String URI = "/jar";
	
	@Post
	public void test(Representation rep) {
		System.out.println(rep.getMediaType());
		try {
			JarInputStream stream = new JarInputStream(rep.getStream());
			String name = stream.getNextEntry().getName();
			System.out.println(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
