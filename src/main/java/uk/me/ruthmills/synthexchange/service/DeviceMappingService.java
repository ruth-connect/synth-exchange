package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;

public interface DeviceMappingService {

	public List<DeviceMapping> getInputs();
	
	public List<DeviceMapping> getOutputs();
	
	public void addInput(DeviceMapping input);
	
	public void addOutput(DeviceMapping output);
}
