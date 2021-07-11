package uk.me.ruthmills.synthexchange.model;

import java.util.List;

public class MidiDevice extends Device {

	private String model;
	private String format;
	private List<MidiParameter> parameters;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<MidiParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<MidiParameter> parameters) {
		this.parameters = parameters;
	}
}
