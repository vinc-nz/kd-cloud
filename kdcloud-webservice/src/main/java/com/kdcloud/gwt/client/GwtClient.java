package com.kdcloud.gwt.client;

import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;
import org.restlet.client.resource.Result;

import sun.security.action.GetLongAction;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtClient implements EntryPoint {
	
	private final DataTableResourceProxy proxy = GWT.create(DataTableResourceProxy.class);
	private ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC; 
	private ChallengeResponse authentication = new ChallengeResponse(scheme, "login", "secret");

	@Override
	public void onModuleLoad() {
		Button getButton = new Button("create");
		final Label label = new Label();
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(getButton);
		panel.add(label);
		RootPanel.get().add(panel);
		
		getButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent evt) {
				proxy.getClientResource().setChallengeResponse(authentication);
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
		});

	}

}
