package uk.me.ruthmills.synthexchange.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MidiService midiService;

	private Grid<DeviceMapping> inputs;
	private Grid<DeviceMapping> outputs;

	private Select<String> midiInputSelect;
	private Select<String> midiOutputSelect;

	public MainView() {
		Label inputsLabel = new Label("Inputs");
		add(inputsLabel);

		inputs = new Grid<>(DeviceMapping.class);
		inputs.setColumns("manufacturer", "model", "connection", "channel");
		inputs.setHeightByRows(true);
		add(inputs);

		Button addInputButton = new Button("Add Input");
		add(addInputButton);

		Label outputsLabel = new Label("Outputs");
		add(outputsLabel);

		outputs = new Grid<>(DeviceMapping.class);
		outputs.setColumns("manufacturer", "model", "connection", "channel");
		outputs.setHeightByRows(true);
		add(outputs);

		Button addOutputButton = new Button("Add Output");
		add(addOutputButton);

		midiInputSelect = new Select<>();
		midiInputSelect.setLabel("MIDI Input");
		add(midiInputSelect);

		midiOutputSelect = new Select<>();
		midiOutputSelect.setLabel("MIDI Output");
		add(midiOutputSelect);
	}

	@PostConstruct
	public void initialise() {
		Set<String> midiDevices = midiService.getMidiDevices();
		midiInputSelect.setItems(midiDevices);
		midiOutputSelect.setItems(midiDevices);
	}
}
