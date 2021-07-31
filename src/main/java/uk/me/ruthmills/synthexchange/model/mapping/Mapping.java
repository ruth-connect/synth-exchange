package uk.me.ruthmills.synthexchange.model.mapping;

public class Mapping {

	private DeviceMapping input;
	private DeviceMapping output;
	private DeviceToDevice mapping;

	public DeviceMapping getInput() {
		return input;
	}

	public void setInput(DeviceMapping input) {
		this.input = input;
	}

	public DeviceMapping getOutput() {
		return output;
	}

	public void setOutput(DeviceMapping output) {
		this.output = output;
	}

	public DeviceToDevice getMapping() {
		return mapping;
	}

	public void setMapping(DeviceToDevice mapping) {
		this.mapping = mapping;
	}
}
