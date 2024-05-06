package org.scottsoft.monitor.thermostat;

import java.util.Optional;

public record ThermostatSampleDTO(Double currentTemp, Double override, Double targetTemp, Double tstate, Long timeMs) {

    public ThermostatSampleDTO(Double currentTemp, Double override, Double targetTemp, Double tstate, Long timeMs) {
        this.currentTemp = Optional.ofNullable(currentTemp).filter(ct -> !ct.isNaN()).orElse(null);
        this.override = Optional.ofNullable(override).filter(o -> !o.isNaN()).orElse(null);
        this.targetTemp = Optional.ofNullable(targetTemp).filter(tt -> !tt.isNaN()).orElse(null);
        this.tstate = Optional.ofNullable(tstate).filter(ts -> !ts.isNaN()).orElse(null);
        this.timeMs = timeMs;
    }

}
