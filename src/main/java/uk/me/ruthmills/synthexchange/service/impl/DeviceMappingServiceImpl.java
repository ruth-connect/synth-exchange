package uk.me.ruthmills.synthexchange.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;

@Service
public class DeviceMappingServiceImpl implements DeviceMappingService {

	private List<DeviceMapping> inputs = new ArrayList<>();
	private List<DeviceMapping> outputs = new ArrayList<>();
	
	@Override
	public List<DeviceMapping> getInputs() {
		return inputs;
	}
	
	@Override
	public List<DeviceMapping> getOutputs() {
		return outputs;
	}
	
	@Override
	public void addInput(DeviceMapping input) {
		inputs.add(input);
	}
	
	@Override
	public void addOutput(DeviceMapping output) {
		outputs.add(output);
	}
}
