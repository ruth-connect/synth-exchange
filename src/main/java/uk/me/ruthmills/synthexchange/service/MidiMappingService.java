package uk.me.ruthmills.synthexchange.service;

import javax.sound.midi.MidiDevice;

public interface MidiMappingService {

	public void mapMidiMessage(MidiDevice.Info midiInputInfo, byte[] message, int length);
}
