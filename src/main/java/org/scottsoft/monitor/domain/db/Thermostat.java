package org.scottsoft.monitor.domain.db;

import javax.persistence.*;

@Entity
@Table(name = "thermostat")
public class Thermostat implements IThermostat {

    @Id
    @Column
	private String id;

    @Column
	private String name;

    @Column(name = "ip_address")
	private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "location_id")
	private Location location;

    @Column(name = "model")
	@Enumerated(EnumType.STRING)
    private ThermostatModel model;

	@Override
	public String getId() {
		return id;
	}

    public void setId(String id) {
        this.id = id;
    }

	@Override
	public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String getIpAddress() {
		return ipAddress;
	}

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

	@Override
	public Location getLocation() {
		return location;
	}

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public ThermostatModel getModel() {
		return model;
	}

	public void setModel(ThermostatModel model) {
		this.model = model;
	}

}
