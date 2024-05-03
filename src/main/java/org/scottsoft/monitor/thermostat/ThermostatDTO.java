package org.scottsoft.monitor.thermostat;

import java.util.UUID;

public record ThermostatDTO(UUID id, UUID locationId, String name, String url) {}
