package uk.me.ruthmills.synthexchange.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.MidiDevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Service
public class MidiServiceImpl implements MidiService {

	@Autowired
	private MidiAdapter midiAdapter;

	private Map<String, MidiDevice.Info> midiDevices = new HashMap<>();

	private MidiDevice midiInputDevice;

	private MidiDevice midiOutputDevice;

	public Set<String> getMidiDevices() {
		midiDevices.clear();
		List<MidiDevice.Info> midiDeviceInfos = midiAdapter.getMidiDevices();
		for (MidiDevice.Info midiDeviceInfo : midiDeviceInfos) {
			midiDevices.put(midiDeviceInfo.getName(), midiDeviceInfo);
		}
		return midiDevices.keySet();
	}

	public MidiDevice getMidiInputDevice() {
		return midiInputDevice;
	}

	public void setMidiInputDevice(String midiInputDeviceInfo) {
	}

	public MidiDevice getMidiOutputDevice() {
		return midiOutputDevice;
	}

	public void setMidiOutputDevice(String midiOutputDeviceInfo) {
	}
}
