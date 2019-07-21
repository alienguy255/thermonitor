package org.scottsoft.monitor.services;

import org.scottsoft.monitor.domain.db.Location;
import org.scottsoft.monitor.services.dao.ILocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	@Autowired
	private ILocationDAO locationDAO;
	
	public Iterable<Location> getAllLocations() {
		return locationDAO.getAllLocations();
	}
	
}
