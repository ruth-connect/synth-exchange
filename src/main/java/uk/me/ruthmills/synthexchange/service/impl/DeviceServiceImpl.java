package uk.me.ruthmills.synthexchange.service.impl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.me.ruthmills.synthexchange.model.device.Device;
import uk.me.ruthmills.synthexchange.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	List<Device> devices = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@PostConstruct
	public void initialise() throws IOException {
		// Read device definitions from JSON files on filesystem.
		Path path = FileSystems.getDefault().getPath("src/main/resources", "devices");
		devices = Files
				.find(path, Integer.MAX_VALUE, (p, basicFileAttributes) -> p.toFile().getName().matches(".*.json"))
				.map(p -> {
					try {
						byte[] bytes = Files.readAllBytes(p);
						String json = new String(bytes);
						logger.info("Device JSON: " + json);
						ObjectMapper mapper = new ObjectMapper();
						return mapper.readValue(json, Device.class);
					} catch (Exception ex) {
						logger.error("Error reading JSON", ex);
						return null;
					}
				}).filter(p -> p != null).sorted(Comparator.comparing(Device::getName)).collect(Collectors.toList());
	}

	@Override
	public List<Device> getDevices() {
		return devices;
	}
}
