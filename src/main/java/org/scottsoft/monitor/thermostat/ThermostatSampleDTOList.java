package org.scottsoft.monitor.thermostat;

import java.util.List;
import java.util.UUID;

public record ThermostatSampleDTOList(UUID thermostatId, List<ThermostatSampleDTO> samples) {}
