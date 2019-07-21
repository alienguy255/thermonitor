package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.db.Location;

public interface ILocationDAO {

	Iterable<Location> getAllLocations();
	
}
