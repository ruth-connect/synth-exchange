package uk.me.ruthmills.synthexchange.model.mapping;

import java.util.ArrayList;
import java.util.List;

public class DeviceToDevice {

	private String from;
	private String to;
	private List<ParameterToParameter> parameters = new ArrayList<>();

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<ParameterToParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterToParameter> parameters) {
		this.parameters = parameters;
	}
}
