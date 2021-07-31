package uk.me.ruthmills.synthexchange.model.mapping;

import java.util.ArrayList;
import java.util.List;

public class ParameterToParameter {

	private String from;
	private String to;
	private Double fromStart;
	private Double toStart;
	private Double fromEnd;
	private Double toEnd;
	private List<ValueToValue> values = new ArrayList<>();

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

	public Double getFromStart() {
		return fromStart;
	}

	public void setFromStart(Double fromStart) {
		this.fromStart = fromStart;
	}

	public Double getToStart() {
		return toStart;
	}

	public void setToStart(Double toStart) {
		this.toStart = toStart;
	}

	public Double getFromEnd() {
		return fromEnd;
	}

	public void setFromEnd(Double fromEnd) {
		this.fromEnd = fromEnd;
	}

	public Double getToEnd() {
		return toEnd;
	}

	public void setToEnd(Double toEnd) {
		this.toEnd = toEnd;
	}

	public List<ValueToValue> getValues() {
		return values;
	}

	public void setValues(List<ValueToValue> values) {
		this.values = values;
	}
}
