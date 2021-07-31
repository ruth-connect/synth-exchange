package uk.me.ruthmills.synthexchange.model.mapping;

import java.util.ArrayList;
import java.util.List;

public class DeviceToDevice {

	private String name;
	private String input;
	private String output;
	private List<ParameterToParameter> parameters = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public List<ParameterToParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterToParameter> parameters) {
		this.parameters = parameters;
	}
}
