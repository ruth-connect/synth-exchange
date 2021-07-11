package uk.me.ruthmills.synthexchange.adapter;

import java.util.List;

import javax.sound.midi.MidiDevice;

public interface MidiAdapter {

	public List<MidiDevice.Info> getMidiDevices();
}
