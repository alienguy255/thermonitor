package org.scottsoft.monitor.location;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public Iterable<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	public Location createLocation(String description, double longitude, double latitude) {
		Location location = new Location(description, longitude, latitude);
		return locationRepository.save(location);
	}

}
