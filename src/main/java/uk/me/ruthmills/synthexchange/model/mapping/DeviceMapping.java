package uk.me.ruthmills.synthexchange.model.mapping;

import uk.me.ruthmills.synthexchange.model.device.Device;

public class DeviceMapping {

	private String manufacturer;
	private String model;
	private String connection;
	private String channel;
	private Device device;

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

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getName() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(manufacturer);
		if (model != null) {
			stringBuilder.append(" ");
			stringBuilder.append(model);
		}
		stringBuilder.append(" - ");
		stringBuilder.append(connection);
		if (channel != null) {
			stringBuilder.append(" channel ");
			stringBuilder.append(channel);
		}
		return stringBuilder.toString();
	}
}
