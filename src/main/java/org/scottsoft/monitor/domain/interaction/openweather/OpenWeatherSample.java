package org.scottsoft.monitor.domain.interaction.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherSample {

	private OpenWeatherMainSample main;
	
	public OpenWeatherSample() {
		
	}
	
	public void setMain(OpenWeatherMainSample main) {
		this.main = main;
	}
	
	public OpenWeatherMainSample getMain() {
		return main;
	}
	
	public double getTemp() {
		return main != null ? Double.parseDouble(main.getTemp()) : Double.NaN;
	}
	
	public double getPressure() {
		return main != null ? Double.parseDouble(main.getPressure()) : Double.NaN;
	}

	@Override
	public String toString() {
		return "OpenWeatherSample [main=" + main + "]";
	}
	
}
