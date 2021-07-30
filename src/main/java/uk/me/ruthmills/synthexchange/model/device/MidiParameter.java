package uk.me.ruthmills.synthexchange.model.device;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MidiParameter {

	private String parameter;
	private String name;
	private String minValue;
	private String maxValue;
	private List<MidiValue> values;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public Optional<MidiValue> findValue(String format, String hex, String channel) throws DecoderException {
		if (values != null && values.size() > 0) {
			return values.stream().filter(
					value -> hex.equals(format.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1))
							.replace("${parameter}", parameter).replace("${value}", value.getValue())))
					.findFirst();
		} else {
			MidiValue midiValue = new MidiValue();
			String match = format.replace("${channel}", Integer.toString(Integer.parseInt(channel) - 1))
					.replace("${parameter}", parameter).replace("${value}", "*");
			int startLength = match.indexOf("*");
			int endLength = match.length() - match.indexOf("*") + 1;
			String valueHex = hex.substring(0, startLength);
			valueHex = valueHex.substring(0, valueHex.length() - endLength);
			int value = ByteBuffer.wrap(Hex.decodeHex(valueHex)).getInt();
			int minValue = ByteBuffer.wrap(Hex.decodeHex(this.minValue)).getInt();
			int maxValue = ByteBuffer.wrap(Hex.decodeHex(this.maxValue)).getInt();
			if (value < minValue) {
				midiValue.setName(Integer.toString(minValue));
				midiValue.setValue(this.minValue);
			} else if (value > maxValue) {
				midiValue.setName(Integer.toString(maxValue));
				midiValue.setValue(this.maxValue);
			} else {
				midiValue.setName(Integer.toString(value));
				midiValue.setValue(valueHex);
			}
			return Optional.of(midiValue);
		}
	}
}
