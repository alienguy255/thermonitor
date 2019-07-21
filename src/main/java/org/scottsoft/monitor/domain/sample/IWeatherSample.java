package org.scottsoft.monitor.domain.sample;

import java.util.Date;

public interface IWeatherSample {

    String getLocationId();
	
	double getCurrentTemp();
	
	Date getTime();

}
