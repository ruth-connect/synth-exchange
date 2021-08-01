package uk.me.ruthmills.synthexchange.service;

import javax.sound.midi.InvalidMidiDataException;

import org.apache.commons.codec.DecoderException;

import uk.me.ruthmills.synthexchange.model.device.MidiParameter;
import uk.me.ruthmills.synthexchange.model.device.MidiValue;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;

public interface MidiSendingService {

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, String value)
			throws DecoderException, InvalidMidiDataException;

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, MidiValue midiValue)
			throws DecoderException, InvalidMidiDataException;
}
