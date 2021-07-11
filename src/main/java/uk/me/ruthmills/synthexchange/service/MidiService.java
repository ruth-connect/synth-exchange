package uk.me.ruthmills.synthexchange.service;

import java.util.Set;

import javax.sound.midi.MidiDevice;

public interface MidiService {

	public Set<String> getMidiDevices();

	public MidiDevice getMidiInputDevice();

	public void setMidiInputDevice(String midiInputDevice);

	public MidiDevice getMidiOutputDevice();

	public void setMidiOutputDevice(String midiOutputDevice);
}
