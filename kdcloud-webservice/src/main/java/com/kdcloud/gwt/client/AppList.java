package com.kdcloud.gwt.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;

public class AppList extends CellTable<String> {
	
	static final String[] APPS = {"ECG Peak Detection"};
	

	public AppList(final Scheduler scheduler) {
		super();
		
		TextColumn<String> column = new TextColumn<String>() {

			@Override
			public String getValue(String object) {
				return object;
			}
		};
		
		this.addColumn(column, "Applications");
		
		ListDataProvider<String> provider = new ListDataProvider<String>();
		provider.addDataDisplay(this);
		
		provider.getList().add(APPS[0]);
		
		this.addCellPreviewHandler(new Handler<String>() {

			@Override
			public void onCellPreview(CellPreviewEvent<String> event) {
				if (event.getNativeEvent().getType().equals("click")) {
					String app = event.getValue();
					Window.alert("you choosed " + app + " application. Now chose the dataset you want to use as input");
					scheduler.setApplication(app);
				}
					
			}
		});
	}
	
	

}
