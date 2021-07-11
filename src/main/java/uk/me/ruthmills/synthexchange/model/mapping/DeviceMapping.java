package uk.me.ruthmills.synthexchange.model.mapping;

public class DeviceMapping {

	private String manufacturer;
	private String model;
	private String channel;
	private String midiInterface;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMidiInterface() {
		return midiInterface;
	}

	public void setMidiInterface(String midiInterface) {
		this.midiInterface = midiInterface;
	}
}
