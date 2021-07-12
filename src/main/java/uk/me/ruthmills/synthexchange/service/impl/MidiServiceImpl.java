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

	public List<MidiDevice.Info> getMidiDevices() {
		return midiAdapter.getMidiDevices().stream().sorted(Comparator.comparing(MidiDevice.Info::getName)).collect(Collectors.toList());
	}
}
