package uk.me.ruthmills.synthexchange.view;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import uk.me.ruthmills.synthexchange.service.MidiService;

@Route
@Component
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MidiService midiService;

	public MainView() {
		Set<String> midiDevices = midiService.getMidiDevices();

		Select<String> midiInputSelect = new Select<>();
		midiInputSelect.setLabel("MIDI Input");
		midiInputSelect.setItems(midiDevices);
		add(midiInputSelect);

		Select<String> midiOutputSelect = new Select<>();
		midiOutputSelect.setLabel("MIDI Output");
		midiOutputSelect.setItems(midiDevices);
		add(midiOutputSelect);
	}
}
