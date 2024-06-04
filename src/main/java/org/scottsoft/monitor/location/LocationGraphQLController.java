package org.scottsoft.monitor.location;

import lombok.RequiredArgsConstructor;
import org.scottsoft.monitor.thermostat.ThermostatDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LocationGraphQLController {

    private final LocationService locationService;

    @QueryMapping
    public List<LocationDTO> locations() {
        return locationService.getAllLocations().stream()
                .map(Location::toDto)
                .toList();
    }

    @QueryMapping
    public LocationDTO location(@Argument String id) {
        Location location = locationService.getLocationById(UUID.fromString(id));
        return Optional.ofNullable(location).map(Location::toDto).orElse(null);
    }

    @SchemaMapping
    public LocationDTO location(ThermostatDTO thermostat) {
        Location location = locationService.getLocationById(thermostat.locationId());
        return Optional.ofNullable(location).map(Location::toDto).orElse(null);
    }

}
