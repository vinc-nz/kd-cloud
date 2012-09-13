package com.kdcloud.client.gwt.mvc;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;
import com.kdcloud.server.domain.Dataset;

public class SummaryTable extends VerticalPanel implements ViewComponent {

	TextColumn<Dataset> nameColumn = new TextColumn<Dataset>() {
		@Override
		public String getValue(Dataset dataset) {
			return dataset.getName();
		}
	};

	TextColumn<Dataset> descriptionColumn = new TextColumn<Dataset>() {
		@Override
		public String getValue(Dataset dataset) {
			return dataset.getDescription();
		}
	};

	TextColumn<Dataset> sizeColumn = new TextColumn<Dataset>() {
		@Override
		public String getValue(Dataset dataset) {
			return Integer.toString(dataset.getSize());
		}
	};

	CellTable<Dataset> table;
	ListDataProvider<Dataset> dataProvider;
	Button newDatasetButton;
	Widget loading;
	Widget noDataBanner;

	Model model;
	Controller controller;

	public SummaryTable(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.dataProvider = new ListDataProvider<Dataset>();

		this.setupTable();
		this.add(table);
		
		this.loading = new Label("please wait while loading data..");
		this.loading.addStyleName("bottomPadding");
		this.loading.addStyleName("leftPadding");
		this.add(loading);
		
		this.noDataBanner = new Label("you do not have any dataset");
		this.noDataBanner.addStyleName("bottomPadding");
		this.noDataBanner.addStyleName("leftPadding");

		this.newDatasetButton = new Button("Create New");
		this.newDatasetButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setupInputLine();
				remove(newDatasetButton);
			}

		});
		this.add(newDatasetButton);
	}

	protected void setupInputLine() {
		final TextBox name = new TextBox();
		final TextBox desc = new TextBox();
		Button okButton = new Button("Ok");
		
		final HorizontalPanel panel = new HorizontalPanel();
		panel.add(name);
		panel.add(desc);
		panel.add(okButton);
		
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				add(loading);
				Dataset d = new Dataset(name.getText(), desc.getText());
				controller.onNewDataset(d);
				remove(panel);
				add(newDatasetButton);
			}
		});

		this.add(panel);
	}

	@Override
	public void refresh() {
		this.remove(loading);
		this.remove(noDataBanner);
		dataProvider.getList().clear();
		dataProvider.getList().addAll(model.data);
		dataProvider.refresh();
		if (model.data.isEmpty()) {
			this.remove(newDatasetButton);
			this.add(noDataBanner);
			this.add(newDatasetButton);
		}
	}

	private void setupTable() {
		table = new CellTable<Dataset>();
		table.addColumn(nameColumn, "Dataset");
		table.addColumn(descriptionColumn, "Description");
		table.addColumn(sizeColumn, "Size");
		table.addCellPreviewHandler(new Handler<Dataset>() {

			@Override
			public void onCellPreview(CellPreviewEvent<Dataset> event) {
				if (event.getNativeEvent().getType().equals("click")) {
					controller.onDatasetSelected(event.getValue());
				}
			}
		});
		dataProvider.addDataDisplay(table);
	}

}
