package uk.me.ruthmills.synthexchange.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
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
		inputs = new Grid<>(DeviceMapping.class);
		add(inputs);

		outputs = new Grid<>(DeviceMapping.class);
		add(outputs);

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
