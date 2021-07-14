package uk.me.ruthmills.synthexchange.adapter.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDeviceReceiver;
import javax.sound.midi.MidiDeviceTransmitter;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;

@Component
public class MidiAdapterImpl implements MidiAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MidiAdapterImpl.class);

	@Override
	public List<MidiDevice.Info> getMidiInputs() {
		return Arrays.asList(MidiSystem.getMidiDeviceInfo()).stream().filter(input -> {
			try (Transmitter transmitter = MidiSystem.getMidiDevice(input).getTransmitter()) {
				return transmitter instanceof MidiDeviceTransmitter;
			} catch (Exception ex) {
				logger.info("Midi Input Unavailable: " + input);
				return false;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public List<MidiDevice.Info> getMidiOutputs() {
		return Arrays.asList(MidiSystem.getMidiDeviceInfo()).stream().filter(output -> {
			try (Receiver receiver = MidiSystem.getMidiDevice(output).getReceiver()) {
				return receiver instanceof MidiDeviceReceiver;
			} catch (Exception ex) {
				logger.info("Midi Output Unavailable: " + output);
				return false;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public Transmitter openMidiInput(MidiDevice.Info midiInputInfo) throws MidiUnavailableException {
		return MidiSystem.getMidiDevice(midiInputInfo).getTransmitter();
	}

	@Override
	public Receiver openMidiOutput(MidiDevice.Info midiOutputInfo) throws MidiUnavailableException {
		return MidiSystem.getMidiDevice(midiOutputInfo).getReceiver();
	}
}
