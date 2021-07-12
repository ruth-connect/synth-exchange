package uk.me.ruthmills.synthexchange.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.adapter.MidiAdapter;
import uk.me.ruthmills.synthexchange.service.MidiService;

@Service
public class MidiServiceImpl implements MidiService {

	@Autowired
	private MidiAdapter midiAdapter;

	@Override
	public List<MidiDevice.Info> getMidiInputs() {
		return midiAdapter.getMidiInputs().stream().sorted(Comparator.comparing(MidiDevice.Info::getName)).collect(Collectors.toList());
	}

	@Override
	public List<MidiDevice.Info> getMidiOutputs() {
		return midiAdapter.getMidiOutputs().stream().sorted(Comparator.comparing(MidiDevice.Info::getName)).collect(Collectors.toList());
	}
}
