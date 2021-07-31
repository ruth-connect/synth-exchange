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

import uk.me.ruthmills.synthexchange.model.mapping.DeviceToDevice;
import uk.me.ruthmills.synthexchange.service.MappingService;

@Service
public class MappingServiceImpl implements MappingService {

	List<DeviceToDevice> mappings = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);

	@PostConstruct
	public void initialise() throws IOException {
		// Read mapping definitions from JSON files on filesystem.
		Path path = FileSystems.getDefault().getPath("src/main/resources", "mappings");
		mappings = Files
				.find(path, Integer.MAX_VALUE, (p, basicFileAttributes) -> p.toFile().getName().matches(".*.json"))
				.map(p -> {
					try {
						byte[] bytes = Files.readAllBytes(p);
						String json = new String(bytes);
						logger.info("Device JSON: " + json);
						ObjectMapper mapper = new ObjectMapper();
						return mapper.readValue(json, DeviceToDevice.class);
					} catch (Exception ex) {
						logger.error("Error reading JSON", ex);
						return null;
					}
				}).filter(p -> p != null).sorted(Comparator.comparing(DeviceToDevice::getName))
				.collect(Collectors.toList());
	}

	@Override
	public List<DeviceToDevice> getMappings() {
		return mappings;
	}
}
