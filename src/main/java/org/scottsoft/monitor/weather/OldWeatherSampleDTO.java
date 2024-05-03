package org.scottsoft.monitor.weather;

import java.util.Date;
import java.util.UUID;

public class OldWeatherSampleDTO {

	private final UUID locationId;
	
	private final double currentTemp;
	
	private final Date time;
	
	public OldWeatherSampleDTO(UUID locationId, double currentTemp, Date time) {
		this.locationId = locationId;
		this.currentTemp = currentTemp;
		this.time = time;
	}
	
	public UUID getLocationId() {
		return locationId;
	}
	
	public Double getCurrentTemp() {
        return !((Double)currentTemp).equals(Double.NaN) ? currentTemp : null;
	}
	
	public Long getTime() {
		return time.getTime();
	}
	
}
