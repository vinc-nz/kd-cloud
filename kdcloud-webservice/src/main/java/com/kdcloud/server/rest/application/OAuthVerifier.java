/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Reference;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.oauth.OAuthServerResource;
import org.restlet.ext.oauth.OAuthUser;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.security.User;
import org.restlet.security.Verifier;

public class OAuthVerifier implements Verifier {
	
	private static final String VALIDATION_URI = "https://www.googleapis.com/oauth2/v1/tokeninfo";
	private static final String JSON_ATTR_EMAIL = "email";
	private static final String ADMIN_FILE = "admin.passwd";
	
	private Logger logger;
	private boolean allowAdministrators;
	
	public OAuthVerifier(Logger logger, boolean allowAdministrators) {
		super();
		this.logger = logger;
		this.allowAdministrators = allowAdministrators;
	}

	@Override
	public int verify(Request request, Response response) {
		if (request.getChallengeResponse() == null)
			return Verifier.RESULT_MISSING;
		//get token
		String userId = request.getChallengeResponse().getIdentifier();
		String secret = new String(request.getChallengeResponse().getSecret());
		if (allowAdministrators)
			try {
				String passwd = getAdministratorDetails().get(userId);
				if (passwd != null && passwd.equals(secret)) {
					request.getClientInfo().setUser(new User(userId));
					return Verifier.RESULT_VALID;
				}
			} catch (IOException e) {
				logger.info("error reading administrators file");
			}
		return verifyOauthToken(request, response, secret);
	}
	
	private Map<String, String> getAdministratorDetails() throws IOException {
		HashMap<String, String> details = new HashMap<String, String>();
		InputStream in = getClass().getClassLoader().getResourceAsStream(ADMIN_FILE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = reader.readLine();
		while (line != null) {
			String[] userDetails = line.split(":");
			if (userDetails.length == 2)
				details.put(userDetails[0], userDetails[1]);
			line = reader.readLine();
		}
		return details;
	}

	public int verifyOauthToken(Request request, Response response, String token) {
		//validate token
		Reference reference = new Reference(VALIDATION_URI);
	    reference.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    ClientResource clientResource = new ClientResource(reference);
	    try {
	    	Representation representation = clientResource.get();
	    	JsonRepresentation json = new JsonRepresentation(representation);
			JSONObject object = json.getJsonObject();
			String user = (String) object.get(JSON_ATTR_EMAIL);
			if (user != null) {
				//valid token
				//add user to client info
				request.getClientInfo().setUser(new OAuthUser(user, token));
				return Verifier.RESULT_VALID;
			}
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "error getting authentication server response", e);
	    }
	    return Verifier.RESULT_INVALID;
	}

}
