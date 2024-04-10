package org.scottsoft.monitor.weather;

import java.util.Date;
import java.util.UUID;

public class WeatherSample implements IWeatherSample {
	
	private final UUID locationId;
	
	private final double currentTemp;
	
	private final Date time;
	
	public WeatherSample(UUID locationId, double currentTemp, Date time) {
		this.locationId = locationId;
		this.currentTemp = currentTemp;
		this.time = time;
	}

	@Override
	public UUID getLocationId() {
		return locationId;
	}

	@Override
	public double getCurrentTemp() {
		return currentTemp;
	}

	@Override
	public Date getTime() {
		return time;
	}
	
}
