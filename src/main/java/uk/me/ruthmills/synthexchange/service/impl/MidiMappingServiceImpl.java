package uk.me.ruthmills.synthexchange.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.model.device.MidiDevice;
import uk.me.ruthmills.synthexchange.model.device.MidiParameter;
import uk.me.ruthmills.synthexchange.model.device.MidiValue;
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
		try {
			// Encode MIDI message to Hex.
			String hex = Hex.encodeHexString(message).substring(0, length * 2).toUpperCase();
			logger.info("MIDI Message received: " + hex);
	
			// Get the device mappings to this MIDI input.
			List<DeviceMapping> deviceMappings = deviceMappingService.getInputs(midiInputInfo.getName());
	
			// Find the corresponding device mapping.
			Optional<DeviceMapping> deviceMappingOptional = deviceMappings.stream()
					.filter(dm -> dm.getDevice() instanceof MidiDevice
							&& ((MidiDevice) dm.getDevice()).matches(hex, dm.getChannel()))
					.findFirst();
	
			// If we found a device mapping, get the MIDI device.
			if (deviceMappingOptional.isPresent()) {
				DeviceMapping deviceMapping = deviceMappingOptional.get();
				MidiDevice midiDevice = (MidiDevice) deviceMapping.getDevice();
				logger.info("Found matching MIDI device: " + midiDevice.getName());
	
				// If we found a matching MIDI parameter, get it.
				Optional<MidiParameter> midiParameterOptional = midiDevice.findParameter(hex, deviceMapping.getChannel());
				if (midiParameterOptional.isPresent()) {
					MidiParameter midiParameter = midiParameterOptional.get();
					logger.info("Found matching MIDI parameter: " + midiParameter.getName());
	
					// If we found a matching MIDI value, get it.
					Optional<MidiValue> midiValueOptional = midiParameter.findValue(midiDevice.getFormat(), hex,
							deviceMapping.getChannel());
					if (midiValueOptional.isPresent()) {
						MidiValue midiValue = midiValueOptional.get();
						logger.info("Found matching MIDI value: " + midiValue.getName());
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception", ex);
		}
	}
}
