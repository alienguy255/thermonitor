package org.scottsoft.monitor.thermostat;

import java.util.UUID;

public record ThermostatUpdateEvent(UUID thermostatId, ThermostatSampleDTO sample) {}
