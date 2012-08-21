package com.kdcloud.client.gwt;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kdcloud.client.gwt.mvc.AppList;
import com.kdcloud.client.gwt.mvc.Controller;
import com.kdcloud.client.gwt.mvc.DetailsPanel;
import com.kdcloud.client.gwt.mvc.Model;
import com.kdcloud.client.gwt.mvc.SummaryTable;
import com.kdcloud.client.gwt.mvc.View;

public class GwtClient implements EntryPoint {

	private static final Auth AUTH = Auth.get();

	private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

	private static final String GOOGLE_CLIENT_ID = "282468236533-1l55nhfurmagse4c9apt11gmmtde3o7i.apps.googleusercontent.com";

	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	
	Controller controller;

	public void requestToken() {
		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
				GOOGLE_CLIENT_ID).withScopes(SCOPE);

		AUTH.login(req, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(String token) {
				controller.onLogin(token);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("unable to open pop-up window, change your browser settings");
			}
		});

	}

	public void onModuleLoad() {
		Model model = new Model();
		View view = new View();
		controller = new Controller(model, view);

		AppList appList = new AppList(model, controller);
		SummaryTable summaryTable = new SummaryTable(model, controller);
		DetailsPanel details = new DetailsPanel(model, controller);
		
		view.add(details);
		view.add(appList);
		view.add(summaryTable);
		
		
		HorizontalPanel tablesPannel = new HorizontalPanel();
		tablesPannel.add(appList);
		tablesPannel.add(summaryTable);
		tablesPannel.add(details);
		tablesPannel.setStyleName("paddedHorizontalPanel");
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(tablesPannel);
		mainPanel.setStyleName("center");

		// Add it to the root panel.
		RootPanel.get().add(mainPanel);
		
		requestToken();
	}

}
