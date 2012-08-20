package com.kdcloud.gwt.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;
import com.kdcloud.server.entity.Dataset;

public class SummaryTable extends VerticalPanel {

	// The list of data to display.
	static final Long id = new Long(1);
	static final List<Dataset> SAMPLE_DATA = Arrays.asList(new Dataset(id,
			"ECG Test Data 1", "no description", 184461), new Dataset(id,
			"ECG Test Data 2", "no description", 162574), new Dataset(id,
			"ECG Test Data 3", "no description", 193670), new Dataset(id,
			"ECG Test Data 4", "no description", 2067));
	
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
	
	CellTable<Dataset> table = new CellTable<Dataset>();
	ListDataProvider<Dataset> dataProvider = new ListDataProvider<Dataset>();

	public SummaryTable(final Controller controller) {
		super();

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
		this.add(table);

		this.setData(SAMPLE_DATA);
	}

	public void setData(List<Dataset> data) {
		dataProvider.getList().clear();
		dataProvider.getList().addAll(data);
	}

}
