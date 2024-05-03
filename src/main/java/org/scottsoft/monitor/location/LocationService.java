package org.scottsoft.monitor.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public Location getLocationById(UUID locationId) {
		return locationRepository.findById(locationId).orElse(null);
	}

	public List<Location> getAllLocations() {
		return StreamSupport.stream(locationRepository.findAll().spliterator(), false).toList();
	}

	public Location createLocation(String description, double longitude, double latitude) {
		return locationRepository.save(new Location(description, longitude, latitude));
	}

}
