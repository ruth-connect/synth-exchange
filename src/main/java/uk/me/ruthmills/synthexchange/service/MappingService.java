package uk.me.ruthmills.synthexchange.service;

import java.util.List;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceToDevice;

public interface MappingService {

	public List<DeviceToDevice> getMappings();
}
