package org.scottsoft.monitor.repositories;

import org.scottsoft.monitor.domain.db.WeatherSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSourceRepository extends CrudRepository<WeatherSource, String> {

}
