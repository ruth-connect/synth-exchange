package uk.me.ruthmills.synthexchange.view;

import javax.sound.midi.MidiDevice;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ListDataProvider<DeviceMapping> inputDataProvider;
	private ListDataProvider<DeviceMapping> outputDataProvider;

	private Grid<DeviceMapping> inputs;
	private Grid<DeviceMapping> outputs;

	private Select<MidiDevice.Info> midiInputSelect;
	private Select<MidiDevice.Info> midiOutputSelect;

	@Autowired
	public MainView(MidiService midiService, DeviceMappingService deviceMappingService, AddInputDialog addInputDialog) {
		Label inputsLabel = new Label("Inputs");
		add(inputsLabel);

		inputDataProvider = DataProvider.ofCollection(deviceMappingService.getInputs());
		inputs = new Grid<>(DeviceMapping.class);
		inputs.setColumns("manufacturer", "model", "connection", "channel");
		inputs.setDataProvider(inputDataProvider);
		inputs.setHeightByRows(true);
		add(inputs);

		Button addInputButton = new Button("Add Input");
		addInputButton.addClickListener(event -> addInputDialog.open());
		add(addInputButton);

		Label outputsLabel = new Label("Outputs");
		add(outputsLabel);

		outputDataProvider = DataProvider.ofCollection(deviceMappingService.getOutputs());
		outputs = new Grid<>(DeviceMapping.class);
		outputs.setColumns("manufacturer", "model", "connection", "channel");
		outputs.setDataProvider(outputDataProvider);
		outputs.setHeightByRows(true);
		add(outputs);

		Button addOutputButton = new Button("Add Output");
		add(addOutputButton);

		midiInputSelect = new Select<>();
		midiInputSelect.setLabel("MIDI Input");
		midiInputSelect.setItemLabelGenerator(MidiDevice.Info::getName);
		midiInputSelect.setItems(midiService.getMidiInputs());
		add(midiInputSelect);

		midiOutputSelect = new Select<>();
		midiOutputSelect.setLabel("MIDI Output");
		midiOutputSelect.setItems(midiService.getMidiOutputs());
		midiOutputSelect.setItemLabelGenerator(MidiDevice.Info::getName);
		add(midiOutputSelect);
	}
}
