package uk.me.ruthmills.synthexchange.io;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;

public class MidiOutput {

	@Autowired
	private MidiAdapter midiAdapter;

	private MidiDevice.Info midiOutputInfo;
	private Receiver midiOutput;

	private static final Logger logger = LoggerFactory.getLogger(MidiOutput.class);

	public MidiOutput(MidiDevice.Info midiOutputInfo) {
		this.midiOutputInfo = midiOutputInfo;
	}

	public void open() throws MidiUnavailableException {
		midiOutput = midiAdapter.openMidiOutput(midiOutputInfo);
		logger.info("Created MIDI output for: " + midiOutputInfo.getName());
	}

	public MidiDevice.Info getMidiOutputInfo() {
		return midiOutputInfo;
	}

	public Receiver getMidiOutput() {
		return midiOutput;
	}

	public void close() {
		midiOutput.close();
		midiOutput = null;
	}
}
