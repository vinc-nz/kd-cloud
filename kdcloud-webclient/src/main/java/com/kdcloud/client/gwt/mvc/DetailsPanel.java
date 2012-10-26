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

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.kdcloud.server.domain.Dataset;

public class DetailsPanel extends VerticalPanel implements ViewComponent {
	
	Model model;
	Controller controller;
	
	Widget defaultView;
	
	public DetailsPanel(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		
		this.defaultView = new Label("click on a dataset to see the infos");
		this.defaultView.setStyleName("topPadding");
		this.add(defaultView);
		this.setSpacing(10);
	}
	
	private void showDetails(final Dataset dataset) {
		this.add(new HTML("<h3>"+dataset.getName()+"</h3>"));
		
		this.add(new Label("the following user can send data to this table:"));
		
	    TextCell textCell = new TextCell();
	    CellList<String> cellList = new CellList<String>(textCell);
	    cellList.setRowCount(dataset.getCommitters().size(), true);
	    cellList.setRowData(0, dataset.getCommitters());
	    cellList.setStyleName("leftPadding");
	    this.add(cellList);
	    
	    this.add(new Hyperlink("Share with others..", Controller.NEW_COMMITTER_HYSTORY_TOKEN));
	    
	    
	    Button deleteButton = new Button("Delete Dataset");
	    deleteButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				controller.onDatasetDeletion(dataset);
			}
		});
	    this.add(deleteButton);
	}
	
	public void refresh() {
		this.clear();
		Dataset dataset = model.selectedDataset;
		if (dataset != null) {
			showDetails(dataset);
		} else {
			add(defaultView);
		}
	}

}
