package org.scottsoft.monitor.weather;

import java.util.Date;
import java.util.UUID;

public interface IWeatherSample {

	UUID getLocationId();
	
	double getCurrentTemp();
	
	Date getTime();

}
