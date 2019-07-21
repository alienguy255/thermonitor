package org.scottsoft.monitor.domain.sample;

import java.util.Date;

public class ThermostatSample implements IThermostatSample {
	
	private final String thermostatId;
	
	private final double currentTemp;
	
	private final Date time;
	
	private final double tmode;
	
	private final double override;
	
	private final double targetTemp;
	
	private final double tstate;
	
	public ThermostatSample(String thermostatId, double currentTemp, double tmode, double override, double targetTemp, double tstate, Date time) {
		this.thermostatId = thermostatId;
		this.currentTemp = currentTemp;
		this.time = time;
		this.tmode = tmode;
		this.override = override;
		this.targetTemp = targetTemp;
		this.tstate = tstate;
	}
	
	@Override
	public String getThermostatId() {
		return thermostatId;
	}
	
	@Override
	public double getCurrentTemp() {
		return currentTemp;
	}

	@Override
	public Date getTime() {
		return time;
	}
	
	@Override
	public double getTmode() {
		return tmode;
	}

	@Override
	public double getOverride() {
		return override;
	}

	@Override
	public double getTargetTemp() {
		return targetTemp;
	}

	@Override
	public double getTstate() {
		return tstate;
	}

}
