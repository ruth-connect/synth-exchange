package uk.me.ruthmills.synthexchange.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import uk.me.ruthmills.synthexchange.service.MidiService;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MidiService midiService;

	private Select<String> midiInputSelect;
	private Select<String> midiOutputSelect;

	public MainView() {
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
