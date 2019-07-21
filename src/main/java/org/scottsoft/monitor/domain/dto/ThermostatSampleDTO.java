package org.scottsoft.monitor.domain.dto;

import java.util.Date;

public class ThermostatSampleDTO {
	
	private final String thermostatId;
	
	private final double currentTemp;
	
	private final double override;
	
	private final double targetTemp;
	
	private final double tstate;
	
	private final Long time;
	
	public ThermostatSampleDTO(String themostatId, double currentTemp, double override, double targetTemp, double tstate, Long time) {
		this.thermostatId = themostatId;
		this.currentTemp = currentTemp;
		this.override = override;
		this.targetTemp = targetTemp;
		this.tstate = tstate;
		this.time = time;
	}
	
	public String getThermostatId() {
		return thermostatId;
	}
	
	public Double getCurrentTemp() {
		return !((Double)currentTemp).equals(Double.NaN) ? currentTemp : null;
	}

	public Double getOverride() {
		return !((Double)override).equals(Double.NaN) ? override : null;
	}

	public Double getTargetTemp() {
		return !((Double)targetTemp).equals(Double.NaN) ? targetTemp : null;
	}

	public Double getTstate() {
		return !((Double)tstate).equals(Double.NaN) ? tstate : null;
	}
	
	public Long getTime() {
		return time;
	}
	
}
