package uk.me.ruthmills.synthexchange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;

@Service
public class DeviceMappingServiceImpl implements DeviceMappingService {

	private List<DeviceMapping> inputs = new ArrayList<>();
	private List<DeviceMapping> outputs = new ArrayList<>();

	private ListDataProvider<DeviceMapping> inputDataProvider = DataProvider.ofCollection(inputs);
	private ListDataProvider<DeviceMapping> outputDataProvider = DataProvider.ofCollection(outputs);

	@Override
	public List<DeviceMapping> getInputs() {
		return inputs;
	}

	@Override
	public List<DeviceMapping> getOutputs() {
		return outputs;
	}

	@Override
	public List<DeviceMapping> getInputs(String connection) {
		return inputs.stream().filter(input -> connection.equals(input.getConnection())).collect(Collectors.toList());
	}

	@Override
	public List<DeviceMapping> getOutputs(String connection) {
		return outputs.stream().filter(output -> connection.equals(output.getConnection()))
				.collect(Collectors.toList());
	}

	@Override
	public ListDataProvider<DeviceMapping> getInputDataProvider() {
		return inputDataProvider;
	}

	@Override
	public ListDataProvider<DeviceMapping> getOutputDataProvider() {
		return outputDataProvider;
	}

	@Override
	public void addInput(DeviceMapping input) {
		inputs.add(input);
		inputDataProvider.refreshAll();
	}

	@Override
	public void addOutput(DeviceMapping output) {
		outputs.add(output);
		outputDataProvider.refreshAll();
	}
}
