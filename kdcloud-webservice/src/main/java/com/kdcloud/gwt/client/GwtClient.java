package com.kdcloud.gwt.client;

import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;
import org.restlet.client.resource.Result;

import com.allen_sauer.gwt.log.client.Log;
import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kdcloud.server.entity.DataTable;

public class GwtClient implements EntryPoint {

	private final DataTableResourceProxy proxy = GWT
			.create(DataTableResourceProxy.class);

	private static final Auth AUTH = Auth.get();

	private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

	// This app's personal client ID assigned by the Google APIs Console
	// (http://code.google.com/apis/console).
	private static final String GOOGLE_CLIENT_ID = "282468236533-1l55nhfurmagse4c9apt11gmmtde3o7i.apps.googleusercontent.com";

	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";

	
	private ChallengeResponse auth = null;
	
	Button getButton;;
	Label label;
	
	DataTable table;

	public void requestToken() {
		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
				GOOGLE_CLIENT_ID).withScopes(SCOPE);

		AUTH.login(req, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(String token) {
				auth = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "gwt", token.toCharArray());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("unable to open pop-up window, change your browser settings");
			}
		});

	}

	@Override
	public void onModuleLoad() {
		requestToken();

		Button getButton = new Button("create");
		Label label = new Label();

		VerticalPanel panel = new VerticalPanel();
		panel.add(getButton);
		panel.add(label);
		panel.setStyleName("style");
		RootPanel.get().add(panel);

		getButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent evt) {
				if (auth == null)
					requestToken();
				else
					createDataset();
				
			}

		});

	}

	protected void createDataset() {
		proxy.getClientResource().setChallengeResponse(auth);
		proxy.getClientResource().setReference("/data");
		proxy.create(new Result<Long>() {

			@Override
			public void onSuccess(Long result) {
				label.setText(Long.toString(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				Log.fatal("error", caught);
			}
		});
	}

}
