package uk.me.ruthmills.synthexchange.adapter;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public interface MidiAdapter {

	public List<MidiDevice.Info> getMidiInputs();

	public List<MidiDevice.Info> getMidiOutputs();

	public Transmitter openMidiInput(MidiDevice.Info midiInputInfo) throws MidiUnavailableException;

	public Receiver openMidiOutput(MidiDevice.Info midiOutputInfo) throws MidiUnavailableException;
}
