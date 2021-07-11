package uk.me.ruthmills.synthexchange.model;

import java.util.List;

public class MidiParameter {

	private String getParameter;
	private String name;
	private String minValue;
	private String maxValue;
	private List<MidiValue> values;

	public String getGetParameter() {
		return getParameter;
	}

	public void setGetParameter(String getParameter) {
		this.getParameter = getParameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public List<MidiValue> getValues() {
		return values;
	}

	public void setValues(List<MidiValue> values) {
		this.values = values;
	}
}
