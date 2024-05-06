package org.scottsoft.monitor.thermostat;

public record TStatSample(Double temp, Double tmode, Double override, Double t_heat, Double tstate) {}
