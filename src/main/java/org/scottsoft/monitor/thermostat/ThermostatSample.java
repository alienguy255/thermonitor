package org.scottsoft.monitor.thermostat;

import java.util.Date;
import java.util.UUID;

public record ThermostatSample(UUID thermostatId, double currentTemp, double targetTemp, double tstate, Date time) implements IThermostatSample {}
