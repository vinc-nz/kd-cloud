package com.kdcloud.lib.client;

import java.io.FileInputStream;
import java.io.IOException;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class DualModeClient {

	public static void main(String[] args) throws IOException {
		Representation representation = new InputRepresentation(
				new FileInputStream("test.jar"),
				MediaType.APPLICATION_OCTET_STREAM);
		ClientResource cr = new ClientResource("http://localhost:8888/jar");
		cr.setRequestEntityBuffering(true);
		cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "admin", "admin");
		cr.post(representation);
	}

}
