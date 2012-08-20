package com.kdcloud.gwt.client;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kdcloud.server.entity.Dataset;

public class DetailsPanel extends VerticalPanel implements ViewComponent {
	
	Model model;
	Controller controller;
	
	public DetailsPanel(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		
		Label label = new Label("click on a dataset to see the infos");
		label.setStyleName("topPadding");
		this.add(label);
		this.setSpacing(10);
	}
	
	public void refresh() {
		
		final Dataset dataset = model.selectedDataset;
		if (dataset != null) {
			this.clear();
			this.add(new HTML("<h3>"+dataset.getName()+"</h3>"));
			
			this.add(new Label("the following user can send data to this table:"));
			
		    TextCell textCell = new TextCell();
		    CellList<String> cellList = new CellList<String>(textCell);
		    cellList.setRowCount(Model.COMMITTERS.size(), true);
		    cellList.setRowData(0, Model.COMMITTERS);
		    cellList.setStyleName("leftPadding");
		    this.add(cellList);
		    
		    this.add(new Hyperlink("Share with others..", null));
		    
		    Button deleteButton = new Button("Delete Dataset");
		    deleteButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					controller.onDatasetDeletion(dataset);
				}
			});
		    this.add(deleteButton);
		}
		
	}

}
