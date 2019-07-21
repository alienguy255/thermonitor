package org.scottsoft.monitor.domain.interaction.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMainSample {

	public String temp;
	
	public String pressure;
	
	public OpenWeatherMainSample() {
		
	}
	
	public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String getPressure() {
		return pressure;
	}
	
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	@Override
	public String toString() {
		return "OpenWeatherMainSample [temp=" + temp + ", pressure=" + pressure
				+ "]";
	}
	
}
