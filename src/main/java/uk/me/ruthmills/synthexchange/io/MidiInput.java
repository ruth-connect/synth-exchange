package uk.me.ruthmills.synthexchange.io;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MidiInput implements Receiver {

	@Autowired
	private MidiAdapter midiAdapter;

	private MidiDevice.Info midiInputInfo;
	private Transmitter midiInput;

	private static final Logger logger = LoggerFactory.getLogger(MidiInput.class);

	public MidiInput(MidiDevice.Info midiInputInfo) {
		this.midiInputInfo = midiInputInfo;
	}

	public void open() throws MidiUnavailableException {
		midiInput = midiAdapter.openMidiInput(midiInputInfo);
		midiInput.setReceiver(this);
		logger.info("Created MIDI input for: " + midiInputInfo.getName());
	}

	public MidiDevice.Info getMidiInputInfo() {
		return midiInputInfo;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		logger.info("MIDI Message received: " + message);
	}

	@Override
	public void close() {
		midiInput.close();
		midiInput = null;
	}
}
