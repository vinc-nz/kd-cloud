package com.kdcloud.gwt.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;

public class AppList extends VerticalPanel {

	public static final List<String> APPS = Arrays.asList("ECG Peak Detection");
	
	TextColumn<String> textColumn = new TextColumn<String>() {

		@Override
		public String getValue(String object) {
			return object;
		}
	};

	public AppList(final Controller controller) {
		super();

		CellTable<String> appTable = new CellTable<String>();
		appTable.addColumn(textColumn, "Applications");
		ListDataProvider<String> provider = new ListDataProvider<String>();
		provider.addDataDisplay(appTable);
		provider.getList().addAll(APPS);
		appTable.addCellPreviewHandler(new Handler<String>() {

			@Override
			public void onCellPreview(CellPreviewEvent<String> event) {
				if (event.getNativeEvent().getType().equals("click")) {
					controller.onApplicationSelected(event.getValue());
				}

			}
		});
		this.add(appTable);

		Widget newApp = new Hyperlink("New Application..", null);
		newApp.setStyleName("leftPadding");
		this.add(newApp);
	}

}
