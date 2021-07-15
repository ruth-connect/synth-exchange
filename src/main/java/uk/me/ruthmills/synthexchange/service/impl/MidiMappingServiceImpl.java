package uk.me.ruthmills.synthexchange.service.impl;

import javax.sound.midi.MidiDevice;

import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.service.MidiMappingService;

@Service
public class MidiMappingServiceImpl implements MidiMappingService {

	@Override
	public void mapMidiMessage(MidiDevice.Info midiInputInfo, byte[] message, int length) {
	}
}
