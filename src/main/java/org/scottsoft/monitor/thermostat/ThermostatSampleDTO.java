package org.scottsoft.monitor.thermostat;

public record ThermostatSampleDTO(Double currentTemp, Double override, Double targetTemp, Double tstate, Long timeMs) {

    public ThermostatSampleDTO(Double currentTemp, Double override, Double targetTemp, Double tstate, Long timeMs) {
        this.currentTemp = currentTemp.isNaN() ? null : currentTemp;
        this.override = override.isNaN() ? null : override;
        this.targetTemp = targetTemp.isNaN() ? null : targetTemp;
        this.tstate = tstate.isNaN() ? null : tstate;
        this.timeMs = timeMs;
    }

}
