package uk.me.ruthmills.synthexchange.model.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParameterToParameter {

	private String input;
	private String output;
	private Double inputStart;
	private Double outputStart;
	private Double inputEnd;
	private Double outputEnd;
	private List<ValueToValue> values = new ArrayList<>();

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

	public Double getInputStart() {
		return inputStart;
	}

	public void setInputStart(Double inputStart) {
		this.inputStart = inputStart;
	}

	public Double getOutputStart() {
		return outputStart;
	}

	public void setOutputStart(Double outputStart) {
		this.outputStart = outputStart;
	}

	public Double getInputEnd() {
		return inputEnd;
	}

	public void setInputEnd(Double inputEnd) {
		this.inputEnd = inputEnd;
	}

	public Double getOutputEnd() {
		return outputEnd;
	}

	public void setOutputEnd(Double outputEnd) {
		this.outputEnd = outputEnd;
	}

	public List<ValueToValue> getValues() {
		return values;
	}

	public void setValues(List<ValueToValue> values) {
		this.values = values;
	}

	public Optional<ValueToValue> findValue(String name) {
		return values.stream().filter(value -> value.getInput().equals(name)).findFirst();
	}
}
