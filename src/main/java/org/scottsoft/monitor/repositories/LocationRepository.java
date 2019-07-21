package org.scottsoft.monitor.repositories;

import org.scottsoft.monitor.domain.db.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, String> {
}
