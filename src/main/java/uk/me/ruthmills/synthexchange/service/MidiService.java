package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

public interface MidiService {

	public List<MidiDevice.Info> getMidiInputs();

	public List<MidiDevice.Info> getMidiOutputs();

	public void openMidiInput(MidiDevice.Info midiInputInfo) throws MidiUnavailableException;

	public void openMidiOutput(MidiDevice.Info midiOutputInfo) throws MidiUnavailableException;

	public void closeMidiInput(MidiDevice.Info midiInputInfo);

	public void closeMidiOutput(MidiDevice.Info midiOutputInfo);
}
