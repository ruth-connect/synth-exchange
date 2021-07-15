package uk.me.ruthmills.synthexchange.io;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;
import uk.me.ruthmills.synthexchange.service.MidiMappingService;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MidiInput implements Receiver {

	@Autowired
	private MidiAdapter midiAdapter;

	@Autowired
	private MidiMappingService midiMappingService;

	private MidiDevice.Info midiInputInfo;
	private Transmitter midiInput;

	private static final Logger logger = LoggerFactory.getLogger(MidiInput.class);

	public MidiInput(MidiDevice.Info midiInputInfo) {
		this.midiInputInfo = midiInputInfo;
	}

	public void open() throws MidiUnavailableException {
		midiInput = midiAdapter.openMidiInput(midiInputInfo);
		midiInput.setReceiver(this);
		logger.info("Created MIDI input for: " + midiInputInfo.getName());
	}

	public MidiDevice.Info getMidiInputInfo() {
		return midiInputInfo;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (message instanceof ShortMessage) {
			ShortMessage shortMessage = (ShortMessage) message;
			midiMappingService.mapMidiMessage(midiInputInfo, shortMessage.getMessage(), shortMessage.getLength());
		} else if (message instanceof SysexMessage) {
			SysexMessage sysexMessage = (SysexMessage) message;
			midiMappingService.mapMidiMessage(midiInputInfo, sysexMessage.getMessage(), sysexMessage.getLength());
		}
	}

	@Override
	public void close() {
		midiInput.close();
		midiInput = null;
	}
}
