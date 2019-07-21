package org.scottsoft.monitor.domain.dto;

import java.util.Date;

public class WeatherSampleDTO {

	private final String locationId;
	
	private final double currentTemp;
	
	private final Date time;
	
	public WeatherSampleDTO(String locationId, double currentTemp, Date time) {
		this.locationId = locationId;
		this.currentTemp = currentTemp;
		this.time = time;
	}
	
	public String getLocationId() {
		return locationId;
	}
	
	public Double getCurrentTemp() {
        return !((Double)currentTemp).equals(Double.NaN) ? currentTemp : null;
	}
	
	public Long getTime() {
		return time.getTime();
	}
	
}
