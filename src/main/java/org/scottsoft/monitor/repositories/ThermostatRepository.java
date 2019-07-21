package org.scottsoft.monitor.repositories;

import org.scottsoft.monitor.domain.db.Location;
import org.scottsoft.monitor.domain.db.Thermostat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThermostatRepository extends CrudRepository<Thermostat, String> {

    Iterable<Thermostat> findByLocation(Location location);

}
