package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.db.Location;
import org.scottsoft.monitor.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDAO implements ILocationDAO {

    @Autowired
    private LocationRepository locationRepository;

	@Override
	public Iterable<Location> getAllLocations() {
	    return locationRepository.findAll();
    }
	
}
