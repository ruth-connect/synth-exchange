package uk.me.ruthmills.synthexchange.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.model.mapping.Mapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private Grid<DeviceMapping> inputs;
	private Grid<DeviceMapping> outputs;
	private Grid<Mapping> mappings;

	@Autowired
	public MainView(DeviceMappingService deviceMappingService, AddInputDialog addInputDialog,
			AddOutputDialog addOutputDialog, AddMappingDialog addMappingDialog) {
		Label inputsLabel = new Label("Inputs");
		add(inputsLabel);

		inputs = new Grid<>(DeviceMapping.class);
		inputs.setColumns("manufacturer", "model", "connection", "channel");
		inputs.setDataProvider(deviceMappingService.getInputDataProvider());
		inputs.setHeightByRows(true);
		add(inputs);

		Button addInputButton = new Button("Add Input");
		addInputButton.addClickListener(event -> addInputDialog.open());
		add(addInputButton);

		Label outputsLabel = new Label("Outputs");
		add(outputsLabel);

		outputs = new Grid<>(DeviceMapping.class);
		outputs.setColumns("manufacturer", "model", "connection", "channel");
		outputs.setDataProvider(deviceMappingService.getOutputDataProvider());
		outputs.setHeightByRows(true);
		add(outputs);

		Button addOutputButton = new Button("Add Output");
		addOutputButton.addClickListener(event -> addOutputDialog.open());
		add(addOutputButton);

		Label mappingsLabel = new Label("Mappings");
		add(mappingsLabel);

		mappings = new Grid<>(Mapping.class);
		mappings.setColumns("input", "output", "mapping");
		mappings.setDataProvider(deviceMappingService.getMappingDataProvider());
		mappings.setHeightByRows(true);
		add(mappings);

		Button addMappingButton = new Button("Add Mapping");
		addMappingButton.addClickListener(event -> addMappingDialog.open());
		add(addMappingButton);
	}
}
