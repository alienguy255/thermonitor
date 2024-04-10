package org.scottsoft.monitor.thermostat;

public record TStatSample(String temp, String tmode, String override, String t_heat, String tstate) {}
