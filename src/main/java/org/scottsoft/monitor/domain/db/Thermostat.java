package org.scottsoft.monitor.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scottsoft.monitor.domain.dto.ThermostatDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public ThermostatDTO toDto() {
        return new ThermostatDTO(id, location.toDto(), name, ipAddress);
    }

}
