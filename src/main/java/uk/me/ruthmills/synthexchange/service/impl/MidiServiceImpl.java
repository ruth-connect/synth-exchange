package uk.me.ruthmills.synthexchange.service.impl;

import java.util.List;

import javax.sound.midi.MidiDevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Service
public class MidiServiceImpl implements MidiService {

	@Autowired
	private MidiAdapter midiAdapter;

	private MidiDevice midiInputDevice;

	private MidiDevice midiOutputDevice;

	public List<MidiDevice.Info> getMidiDevices() {
		return midiAdapter.getMidiDevices();
	}

	public MidiDevice getMidiInputDevice() {
		return midiInputDevice;
	}

	public void setMidiInputDevice(MidiDevice.Info midiInputDeviceInfo) {
	}

	public MidiDevice getMidiOutputDevice() {
		return midiOutputDevice;
	}

	public void setMidiOutputDevice(MidiDevice.Info midiOutputDeviceInfo) {
	}
}
