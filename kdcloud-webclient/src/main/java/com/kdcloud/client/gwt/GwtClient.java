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
package com.kdcloud.client.gwt;
//
//
//import com.google.api.gwt.oauth2.client.Auth;
//import com.google.api.gwt.oauth2.client.AuthRequest;
//import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.Widget;
//import com.kdcloud.client.gwt.mvc.AppList;
//import com.kdcloud.client.gwt.mvc.Controller;
//import com.kdcloud.client.gwt.mvc.DetailsPanel;
//import com.kdcloud.client.gwt.mvc.Model;
//import com.kdcloud.client.gwt.mvc.SummaryTable;
//import com.kdcloud.client.gwt.mvc.UserPanel;
//import com.kdcloud.client.gwt.mvc.View;
//
public class GwtClient implements EntryPoint {
//
//	private static final Auth AUTH = Auth.get();
//
//	private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
//
//	private static final String GOOGLE_CLIENT_ID = "282468236533-1l55nhfurmagse4c9apt11gmmtde3o7i.apps.googleusercontent.com";
//
//	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
//	
//	private static final String LOGIN_MESSAGE = "You are not authenticated. " +
//			"Please disable pop-ups for this site then reload the page to sign in with your Google Account";
//	
//	
//	Widget notLoggedBanner = new Label(LOGIN_MESSAGE);
//	
//	Model model = new Model();
//	View view = new View();
//	Controller controller = new Controller(model, view);
//	
//
//	public void requestToken() {
//		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
//				GOOGLE_CLIENT_ID).withScopes(SCOPE);
//
//		AUTH.login(req, new Callback<String, Throwable>() {
//			@Override
//			public void onSuccess(String token) {
//				init();
//				controller.onLogin(token);
//			}
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("unable to open pop-up window, change your browser settings");
//			}
//		});
//
//	}
//
//	public void init() {
//		RootPanel.get().remove(notLoggedBanner);
//		
//		AppList appList = new AppList(model, controller);
//		SummaryTable summaryTable = new SummaryTable(model, controller);
//		DetailsPanel details = new DetailsPanel(model, controller);
//		UserPanel userPanel = new UserPanel(model);
//		
//		view.add(details);
//		view.add(appList);
//		view.add(summaryTable);
//		view.add(userPanel);
//		
//		
//		HorizontalPanel tablesPannel = new HorizontalPanel();
//		tablesPannel.add(appList);
//		tablesPannel.add(summaryTable);
//		tablesPannel.add(details);
//		tablesPannel.setStyleName("paddedHorizontalPanel");
//		
//		VerticalPanel mainPanel = new VerticalPanel();
//		mainPanel.add(userPanel);
//		mainPanel.add(tablesPannel);
//		mainPanel.setStyleName("center");
//
//		RootPanel.get().add(mainPanel);
//	}
//
	@Override
	public void onModuleLoad() {
//		notLoggedBanner.setStyleName("center");
//		RootPanel.get().add(notLoggedBanner);
//		requestToken();
	}
//
}
