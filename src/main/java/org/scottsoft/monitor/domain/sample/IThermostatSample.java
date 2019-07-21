package org.scottsoft.monitor.domain.sample;

import java.util.Date;

public interface IThermostatSample {

	String getThermostatId();

	double getCurrentTemp();

    double getTmode();

    double getOverride();

    double getTargetTemp();

    double getTstate();

	Date getTime();

}
