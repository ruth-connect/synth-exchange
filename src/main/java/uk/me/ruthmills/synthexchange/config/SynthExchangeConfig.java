package uk.me.ruthmills.synthexchange.config;

import javax.sound.midi.MidiDevice;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import uk.me.ruthmills.synthexchange.io.MidiInput;
import uk.me.ruthmills.synthexchange.io.MidiOutput;

@Configuration
public class SynthExchangeConfig {

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public MidiInput createMidiInputThread(MidiDevice.Info midiInputInfo) {
		{
			return new MidiInput(midiInputInfo);
		}
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public MidiOutput createMidiOutputThread(MidiDevice.Info midiOutputInfo) {
		{
			return new MidiOutput(midiOutputInfo);
		}
	}
}
