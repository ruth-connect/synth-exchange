package uk.me.ruthmills.synthexchange.service.impl;

import java.util.Optional;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, String value) {
		MidiValue midiValue = new MidiValue();
		midiValue.setName(value);

		// TODO - values may be more than one byte long for some synthesizers.
		byte[] data = new byte[1];
		data[0] = (byte) Integer.parseInt(value);
		midiValue.setValue(Hex.encodeHexString(data));

		sendMidiMessage(deviceMapping, midiParameter, midiValue);
	}

	public void sendMidiMessage(DeviceMapping deviceMapping, MidiParameter midiParameter, MidiValue midiValue) {
		Optional<javax.sound.midi.MidiDevice.Info> midiDeviceInfoOptional = midiService
				.getMidiOutput(deviceMapping.getConnection());
		if (midiDeviceInfoOptional.isPresent()) {
			javax.sound.midi.MidiDevice.Info midiDeviceInfo = midiDeviceInfoOptional.get();
			MidiDevice midiDevice = (MidiDevice) deviceMapping.getDevice();
		}
	}
}
