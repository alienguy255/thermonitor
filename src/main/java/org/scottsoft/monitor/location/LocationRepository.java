package org.scottsoft.monitor.location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {
}
