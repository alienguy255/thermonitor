package org.scottsoft.monitor.domain.db;

public interface IThermostat {

	String getId();
	
	String getName();
	
	String getIpAddress();
	
	Location getLocation();

	ThermostatModel getModel();

}
