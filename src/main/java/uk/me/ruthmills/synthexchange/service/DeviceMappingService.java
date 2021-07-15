package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import com.vaadin.flow.data.provider.ListDataProvider;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;

public interface DeviceMappingService {

	public List<DeviceMapping> getInputs();

	public List<DeviceMapping> getOutputs();

	public List<DeviceMapping> getInputs(String connection);

	public List<DeviceMapping> getOutputs(String connection);

	public ListDataProvider<DeviceMapping> getInputDataProvider();

	public ListDataProvider<DeviceMapping> getOutputDataProvider();

	public void addInput(DeviceMapping input);

	public void addOutput(DeviceMapping output);
}
