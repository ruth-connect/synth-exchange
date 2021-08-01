package uk.me.ruthmills.synthexchange.service;

import java.util.List;
import java.util.Optional;

import com.vaadin.flow.data.provider.ListDataProvider;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.model.mapping.Mapping;

public interface DeviceMappingService {

	public List<DeviceMapping> getInputs();

	public List<DeviceMapping> getOutputs();

	public Optional<DeviceMapping> getOutput(String name);

	public List<Mapping> getMappings();

	public List<DeviceMapping> getInputs(String connection);

	public List<DeviceMapping> getOutputs(String connection);

	public List<Mapping> getMappings(DeviceMapping deviceMapping);

	public ListDataProvider<DeviceMapping> getInputDataProvider();

	public ListDataProvider<DeviceMapping> getOutputDataProvider();

	public ListDataProvider<Mapping> getMappingDataProvider();

	public void addInput(DeviceMapping input);

	public void addOutput(DeviceMapping output);

	public void addMapping(Mapping mapping);
}
