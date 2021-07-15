package uk.me.ruthmills.synthexchange.service.impl;

import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.model.device.MidiDevice;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.MidiMappingService;

@Service
public class MidiMappingServiceImpl implements MidiMappingService {

	@Autowired
	private DeviceMappingService deviceMappingService;

	private static final Logger logger = LoggerFactory.getLogger(MidiMappingServiceImpl.class);

	@Override
	public void mapMidiMessage(javax.sound.midi.MidiDevice.Info midiInputInfo, byte[] message, int length) {

		// Encode MIDI message to Hex.
		String hex = Hex.encodeHexString(message).substring(0, length * 2);
		logger.info("MIDI Message received: " + hex);

		// Get the device mappings to this MIDI input.
		List<DeviceMapping> deviceMappings = deviceMappingService.getOutputs(midiInputInfo.getName());

		// Find the corresponding MIDI device.
		MidiDevice midiDevice = (MidiDevice) deviceMappings.stream()
				.filter(deviceMapping -> deviceMapping.getDevice() instanceof MidiDevice
						&& ((MidiDevice) deviceMapping.getDevice()).matches(hex, deviceMapping.getChannel()))
				.findFirst().get().getDevice();

		logger.info("Matching Device: " + midiDevice.getName());
	}
}
