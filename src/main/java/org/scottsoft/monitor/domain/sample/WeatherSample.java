package org.scottsoft.monitor.domain.sample;

import java.util.Date;

public class WeatherSample implements IWeatherSample {
	
	private final String locationId;
	
	private final double currentTemp;
	
	private final Date time;
	
	public WeatherSample(String locationId, double currentTemp, Date time) {
		this.locationId = locationId;
		this.currentTemp = currentTemp;
		this.time = time;
	}

	@Override
	public String getLocationId() {
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
