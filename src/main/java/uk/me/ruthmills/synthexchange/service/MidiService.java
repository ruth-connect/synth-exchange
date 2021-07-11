package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import javax.sound.midi.MidiDevice;

public interface MidiService {

	public List<MidiDevice.Info> getMidiDevices();

	public MidiDevice getMidiInputDevice();

	public void setMidiInputDevice(MidiDevice.Info midiInputDeviceInfo);

	public MidiDevice getMidiOutputDevice();

	public void setMidiOutputDevice(MidiDevice.Info midiOutputDeviceInfo);
}
