package uk.me.ruthmills.synthexchange.model.device;

import java.util.List;
import java.util.Optional;

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

	public String getName() {
		return getManufacturer() + " - " + model;
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

	public boolean matches(String hex, String channel) {
		return hex.startsWith(format.substring(0, format.indexOf("${parameter}")).replace("${channel}",
				Integer.toString(Integer.parseInt(channel) - 1)));
	}

	public Optional<MidiParameter> findParameter(String hex, String channel) {
		return parameters.stream()
				.filter(parameter -> hex.startsWith(format.substring(0, format.indexOf("${value}"))
						.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1))
						.replace("${parameter}", parameter.getParameter())))
				.findFirst();
	}

	public Optional<MidiParameter> findParameter(String name) {
		return parameters.stream().filter(parameter -> parameter.getName().equals(name)).findFirst();
	}

	public String getMessage(String channel, MidiParameter midiParameter, MidiValue midiValue) {
		return format.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1))
				.replace("${parameter}", midiParameter.getParameter()).replace("${value}", midiValue.getValue());
	}
}
