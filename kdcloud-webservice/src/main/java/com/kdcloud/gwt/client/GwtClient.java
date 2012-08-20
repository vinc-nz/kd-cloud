package com.kdcloud.gwt.client;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtClient implements EntryPoint {

	private static final Auth AUTH = Auth.get();

	private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

	private static final String GOOGLE_CLIENT_ID = "282468236533-1l55nhfurmagse4c9apt11gmmtde3o7i.apps.googleusercontent.com";

	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	
	private String accessToken;


	public void requestToken() {
		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
				GOOGLE_CLIENT_ID).withScopes(SCOPE);

		AUTH.login(req, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(String token) {
				GwtClient.this.accessToken = token;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("unable to open pop-up window, change your browser settings");
			}
		});

	}

	public void onModuleLoad() {

		Scheduler scheduler = new Scheduler();
		DetailsPanel details = new DetailsPanel();
		Controller controller = new Controller(details, scheduler);
		SummaryTable table = new SummaryTable(controller);
		AppList list = new AppList(controller);
		
		HorizontalPanel tablesPannel = new HorizontalPanel();
		tablesPannel.add(list);
		tablesPannel.add(table);
		tablesPannel.add(details);
		tablesPannel.setStyleName("paddedHorizontalPanel");
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(tablesPannel);
		mainPanel.setStyleName("center");

		// Add it to the root panel.
		RootPanel.get().add(mainPanel);
	}

}
