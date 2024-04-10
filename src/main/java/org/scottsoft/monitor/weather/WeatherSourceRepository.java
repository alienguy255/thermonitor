package org.scottsoft.monitor.weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeatherSourceRepository extends CrudRepository<WeatherSource, UUID> {

}
