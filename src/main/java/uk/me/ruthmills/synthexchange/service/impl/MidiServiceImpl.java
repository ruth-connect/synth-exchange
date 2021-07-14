package uk.me.ruthmills.synthexchange.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;
import uk.me.ruthmills.synthexchange.config.SynthExchangeConfig;
import uk.me.ruthmills.synthexchange.io.MidiInput;
import uk.me.ruthmills.synthexchange.io.MidiOutput;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Service
public class MidiServiceImpl implements MidiService {

	@Autowired
	private SynthExchangeConfig synthExchangeConfig;

	@Autowired
	private MidiAdapter midiAdapter;

	private Map<MidiDevice.Info, MidiInput> midiInputs = new ConcurrentHashMap<>();
	private Map<MidiDevice.Info, MidiOutput> midiOutputs = new ConcurrentHashMap<>();

	@Override
	public List<MidiDevice.Info> getMidiInputs() {
		return midiAdapter.getMidiInputs().stream().sorted(Comparator.comparing(MidiDevice.Info::getName))
				.collect(Collectors.toList());
	}

	@Override
	public List<MidiDevice.Info> getMidiOutputs() {
		return midiAdapter.getMidiOutputs().stream().sorted(Comparator.comparing(MidiDevice.Info::getName))
				.collect(Collectors.toList());
	}

	@Override
	public void openMidiInput(MidiDevice.Info midiInputInfo) throws MidiUnavailableException {
		MidiInput midiInput = midiInputs.get(midiInputInfo);
		if (midiInput == null) {
			midiInput = synthExchangeConfig.createMidiInputThread(midiInputInfo);
			midiInput.open();
			midiInputs.put(midiInputInfo, midiInput);
		}
	}

	@Override
	public void openMidiOutput(MidiDevice.Info midiOutputInfo) throws MidiUnavailableException {
		MidiOutput midiOutput = midiOutputs.get(midiOutputInfo);
		if (midiOutput == null) {
			midiOutput = synthExchangeConfig.createMidiOutputThread(midiOutputInfo);
			midiOutput.open();
			midiOutputs.put(midiOutputInfo, midiOutput);
		}
	}

	@Override
	public void closeMidiInput(MidiDevice.Info midiInputInfo) {
		MidiInput midiInput = midiInputs.get(midiInputInfo);
		if (midiInput != null) {
			midiInput.close();
			midiInputs.remove(midiInputInfo);
		}
	}

	@Override
	public void closeMidiOutput(MidiDevice.Info midiOutputInfo) {
		MidiOutput midiOutput = midiOutputs.get(midiOutputInfo);
		if (midiOutput != null) {
			midiOutput.close();
			midiOutputs.remove(midiOutputInfo);
		}
	}
}
