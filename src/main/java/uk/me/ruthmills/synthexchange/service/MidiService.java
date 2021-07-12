package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import javax.sound.midi.MidiDevice;

public interface MidiService {

	public List<MidiDevice.Info> getMidiDevices();
}
