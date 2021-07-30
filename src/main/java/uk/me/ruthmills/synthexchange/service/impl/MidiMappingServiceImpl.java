package uk.me.ruthmills.synthexchange.service.impl;

import java.util.List;
import java.util.Optional;

import javax.sound.midi.InvalidMidiDataException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.model.device.MidiDevice;
import uk.me.ruthmills.synthexchange.model.device.MidiParameter;
import uk.me.ruthmills.synthexchange.model.device.MidiValue;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.model.mapping.Mapping;
import uk.me.ruthmills.synthexchange.model.mapping.ParameterToParameter;
import uk.me.ruthmills.synthexchange.model.mapping.ValueToValue;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.MidiMappingService;
import uk.me.ruthmills.synthexchange.service.MidiSendingService;

@Service
public class MidiMappingServiceImpl implements MidiMappingService {

	@Autowired
	private DeviceMappingService deviceMappingService;

	@Autowired
	private MidiSendingService midiSendingService;

	private static final Logger logger = LoggerFactory.getLogger(MidiMappingServiceImpl.class);

	@Override
	public void mapMidiMessage(javax.sound.midi.MidiDevice.Info midiInputInfo, byte[] message, int length) {
		try {
			// Encode MIDI message to Hex.
			String hex = Hex.encodeHexString(message).substring(0, length * 2).toUpperCase();
			
			// Filter out Midi Clock (F8) and Active Sensing (FE).
			if (!"F8".equals(hex) && !"FE".equals(hex)) {
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
					Optional<MidiParameter> midiParameterOptional = midiDevice.findParameter(hex,
							deviceMapping.getChannel());
					if (midiParameterOptional.isPresent()) {
						MidiParameter midiParameter = midiParameterOptional.get();
						logger.info("Found matching MIDI parameter: " + midiParameter.getName());
	
						// If we found a matching MIDI value, get it.
						Optional<MidiValue> midiValueOptional = midiParameter.findValue(midiDevice.getFormat(), hex,
								deviceMapping.getChannel());
						if (midiValueOptional.isPresent()) {
							MidiValue midiValue = midiValueOptional.get();
							logger.info("Found matching MIDI value: " + midiValue.getName());
	
							// Map the MIDI message.
							mapMidiMessage(deviceMapping, midiParameter, midiValue);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception", ex);
		}
	}

	private void mapMidiMessage(DeviceMapping inputDeviceMapping, MidiParameter inputMidiParameter,
			MidiValue inputMidiValue) throws DecoderException, InvalidMidiDataException {
		List<Mapping> mappings = deviceMappingService.getMappings(inputDeviceMapping);
		for (Mapping mapping : mappings) {
			logger.info("Found mapping: " + mapping.getMapping().getName());
			logger.info("Mapping device " + mapping.getInput().getName() + " to " + mapping.getOutput().getName());

			Optional<DeviceMapping> outputDeviceMappingOptional = deviceMappingService
					.getOutput(mapping.getOutput().getName());
			if (outputDeviceMappingOptional.isPresent()) {
				DeviceMapping outputDeviceMapping = outputDeviceMappingOptional.get();
				logger.info("Found output device: " + outputDeviceMapping.getName());

				// TODO - make this generic in terms of device, i.e. not just MIDI.
				MidiDevice outputMidiDevice = (MidiDevice) outputDeviceMapping.getDevice();

				Optional<ParameterToParameter> parameterMappingOptional = mapping.getMapping()
						.findParameter(inputMidiParameter.getName());
				if (parameterMappingOptional.isPresent()) {

					ParameterToParameter parameter = parameterMappingOptional.get();
					logger.info("Mapping parameter " + parameter.getInput() + " to " + parameter.getOutput());

					Optional<MidiParameter> outputMidiParameterOptional = outputMidiDevice
							.findParameter(parameter.getOutput());
					if (outputMidiParameterOptional.isPresent()) {
						MidiParameter outputMidiParameter = outputMidiParameterOptional.get();

						if (parameter.getValues() != null && parameter.getValues().size() > 0) {

							// We have a defined set of values specified. Convert between them.
							Optional<ValueToValue> valueMappingOptional = parameter.findValue(inputMidiValue.getName());
							if (valueMappingOptional.isPresent()) {
								ValueToValue value = valueMappingOptional.get();
								logger.info("Mapping value: " + value.getInput() + " to " + value.getOutput());

								Optional<MidiValue> outputMidiValueOptional = outputMidiParameter
										.findValue(value.getOutput());
								if (outputMidiValueOptional.isPresent()) {
									MidiValue outputMidiValue = outputMidiValueOptional.get();
									midiSendingService.sendMidiMessage(outputDeviceMapping, outputMidiParameter,
											outputMidiValue);
								}
							}
						} else if (parameter.getInputStart() != null && parameter.getOutputStart() != null
								&& parameter.getInputEnd() != null && parameter.getOutputEnd() != null) {

							// We have a scaling range specified. Scale the input to the output.
							Double inputStart = parameter.getInputStart();
							Double outputStart = parameter.getOutputStart();
							Double inputEnd = parameter.getInputEnd();
							Double outputEnd = parameter.getOutputEnd();

							Double input = Double.parseDouble(inputMidiValue.getName());
							if (input < inputStart) {
								input = inputStart;
							}
							if (input > inputEnd) {
								input = inputEnd;
							}

							Double output = (Math.abs(input - inputStart) / (Math.abs(inputEnd - inputStart) + 1))
									* (Math.abs(outputEnd - outputStart) + 1);
							if ((inputEnd > inputStart && outputEnd < outputStart)
									|| (inputEnd < inputStart && outputEnd > outputStart)) {
								output = Math.abs(Math.abs(outputEnd - outputStart) - output);
							}

							String outputString = Integer.toString((int) Math.floor(output));

							logger.info("Mapping value: " + inputMidiValue.getName() + " to " + outputString);

							midiSendingService.sendMidiMessage(outputDeviceMapping, outputMidiParameter, outputString);
						} else {

							// No conversion required. Pass the input to the output verbatim.
							logger.info(
									"Mapping value: " + inputMidiValue.getName() + " to " + inputMidiValue.getName());

							midiSendingService.sendMidiMessage(outputDeviceMapping, outputMidiParameter,
									inputMidiValue);
						}
					}
				}
			}
		}
	}
}
