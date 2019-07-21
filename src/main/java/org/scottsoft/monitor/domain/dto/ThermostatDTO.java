package org.scottsoft.monitor.domain.dto;

import org.scottsoft.monitor.domain.db.Location;

public class ThermostatDTO {

	private final String id;
	
	private final Location location;
	
	private final String name;
	
	private final String ipAddress;
	
	public ThermostatDTO(String id, Location location, String name, String ipAddress) {
		this.id = id;
		this.location = location;
		this.name = name;
		this.ipAddress = ipAddress;
	}

	public String getId() {
		return id;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}

	public String getIpAddress() {
		return ipAddress;
	}
}
