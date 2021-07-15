package uk.me.ruthmills.synthexchange.model.device;

import java.util.List;
import java.util.Optional;

public class MidiDevice extends Device {

	private String model;
	private String match;
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

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
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
		return hex.startsWith(match.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1)));
	}
	
	public Optional<MidiParameter> findParameter(String hex, String channel) {
		return parameters.stream().filter(parameter ->
			hex.startsWith(
					match.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1)) + parameter.getParameter())).findFirst();
	}
}
