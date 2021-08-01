package uk.me.ruthmills.synthexchange.service.impl;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.io.MidiOutput;
import uk.me.ruthmills.synthexchange.model.device.MidiDevice;
import uk.me.ruthmills.synthexchange.model.device.MidiParameter;
import uk.me.ruthmills.synthexchange.model.device.MidiValue;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.MidiSendingService;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Service
public class MidiSendingServiceImpl implements MidiSendingService {

	@Autowired
	public MidiService midiService;

	private static final Logger logger = LoggerFactory.getLogger(MidiSendingServiceImpl.class);

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, String value)
			throws DecoderException, InvalidMidiDataException {
		MidiValue midiValue = new MidiValue();
		midiValue.setName(value);

		// TODO - values may be more than one byte long for some synthesizers.
		byte[] data = new byte[1];
		data[0] = (byte) Integer.parseInt(value);
		midiValue.setValue(Hex.encodeHexString(data));

		sendMidiMessage(deviceMapping, midiParameter, midiValue);
	}

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, MidiValue midiValue)
			throws DecoderException, InvalidMidiDataException {
		MidiOutput midiOutput = midiService.getMidiOutput(deviceMapping.getConnection());
		if (midiOutput != null) {
			MidiDevice midiDevice = (MidiDevice) deviceMapping.getDevice();
			String midiHex = midiDevice.getMessage(deviceMapping.getChannel(), midiParameter, midiValue);
			MidiMessage midiMessage = createMidiMessage(midiHex);
			logger.info("Sending MIDI message to: " + deviceMapping.getName());
			midiOutput.getMidiOutput().send(midiMessage, 0L);
		}
	}

	private MidiMessage createMidiMessage(String midiHex) throws DecoderException, InvalidMidiDataException {
		byte[] data = Hex.decodeHex(midiHex);
		if (midiHex.startsWith("F0")) {
			logger.info("Creating Sysex Message: " + midiHex);
			SysexMessage sysexMessage = new SysexMessage();
			sysexMessage.setMessage(data, data.length);
			return sysexMessage;
		} else {
			logger.info("Creating Short Message: " + midiHex);
			ShortMessage shortMessage = new ShortMessage();
			shortMessage.setMessage((int) data[0], (int) data[1], (int) data[2]);
			return shortMessage;
		}
	}
}
