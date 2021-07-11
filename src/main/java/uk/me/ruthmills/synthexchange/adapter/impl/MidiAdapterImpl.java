package uk.me.ruthmills.synthexchange.adapter.impl;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

import org.springframework.stereotype.Component;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;

@Component
public class MidiAdapterImpl implements MidiAdapter {

	public List<MidiDevice.Info> getMidiDevices() {
		return Arrays.asList(MidiSystem.getMidiDeviceInfo());
	}
}
