package com.kdcloud.gwt.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kdcloud.server.entity.Dataset;

public class DetailsPanel extends VerticalPanel {
	
	private static final List<String> COMMITTERS = Arrays.asList("g.fortino@gmail.com",
			"g.difatta@gmail.com", "v.pirrone@gmail.com", "d.parisi@gmail.com");

	
	public DetailsPanel() {
		Label label = new Label("click on a dataset to see the infos");
		label.setStyleName("topPadding");
		this.add(label);
		this.setSpacing(10);
	}
	
	public void setDataset(Dataset dataset) {
		this.clear();
		this.add(new HTML("<h3>"+dataset.getName()+"</h3>"));
		
		this.add(new Label("the following user can send data to this table:"));
		
	    TextCell textCell = new TextCell();
	    CellList<String> cellList = new CellList<String>(textCell);
	    cellList.setRowCount(COMMITTERS.size(), true);
	    cellList.setRowData(0, COMMITTERS);
	    cellList.setStyleName("leftPadding");
	    this.add(cellList);
	    
	    this.add(new Hyperlink("Share with others..", null));
	    
	    this.add(new Button("Delete Dataset"));
		
	}

}
