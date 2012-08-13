package com.kdcloud.gwt.client;

import org.restlet.client.resource.Result;

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
				proxy.getClientResource().setReference("/data");
				proxy.create(new Result<Long>() {
					
					@Override
					public void onSuccess(Long result) {
						label.setText(Long.toString(result));
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("non funziona un cazzo");
					}
				});
			}
		});

	}

}
