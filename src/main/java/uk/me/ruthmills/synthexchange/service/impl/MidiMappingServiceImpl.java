package uk.me.ruthmills.synthexchange.service.impl;

import java.util.List;
import java.util.Optional;

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
		String hex = Hex.encodeHexString(message).substring(0, length * 2).toUpperCase();
		logger.info("MIDI Message received: " + hex);

		// Get the device mappings to this MIDI input.
		List<DeviceMapping> deviceMappings = deviceMappingService.getInputs(midiInputInfo.getName());

		// Find the corresponding device mapping.
		Optional<DeviceMapping> deviceMapping = deviceMappings.stream()
				.filter(dm -> dm.getDevice() instanceof MidiDevice
						&& ((MidiDevice) dm.getDevice()).matches(hex, dm.getChannel()))
				.findFirst();
		
		// If we found a device mapping, get the MIDI device.
		if (deviceMapping.isPresent()) {
			MidiDevice midiDevice = (MidiDevice)deviceMapping.get().getDevice();
			logger.info("Found matching MIDI device: " + midiDevice.getName());
		}
	}
}
