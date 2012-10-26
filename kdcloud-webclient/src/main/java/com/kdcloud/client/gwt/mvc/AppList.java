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
package com.kdcloud.client.gwt.mvc;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;

public class AppList extends VerticalPanel implements ViewComponent {
	
	
	TextColumn<String> textColumn = new TextColumn<String>() {

		@Override
		public String getValue(String object) {
			return object;
		}
	};
	
	CellTable<String> appTable = new CellTable<String>();
	
	Model model;

	Controller controller;

	public AppList(Model model, final Controller controller) {
		super();
		this.model = model;
		this.controller = controller;

		appTable.addColumn(textColumn, "Applications");
		ListDataProvider<String> provider = new ListDataProvider<String>();
		provider.addDataDisplay(appTable);
		provider.getList().addAll(model.apps);
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
	
	public void setupHandlers(final Controller controller) {
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
