package org.scottsoft.monitor.thermostat;

import org.scottsoft.monitor.location.LocationDTO;
import java.util.UUID;

public record ThermostatDTO(UUID id, LocationDTO location, String name, String url) {}
