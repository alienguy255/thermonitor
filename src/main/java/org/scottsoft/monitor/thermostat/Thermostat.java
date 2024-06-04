package org.scottsoft.monitor.thermostat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scottsoft.monitor.location.Location;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thermostat_source")
public class Thermostat {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

    @Column
	private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "location_id")
	private Location location;

    @Column(name = "source_type")
    @Enumerated(EnumType.STRING)
    private ThermostatSourceType sourceType;

    @Column(name = "auth_token")
    private String authToken;

    public Thermostat(String name, String url, Location location, ThermostatSourceType sourceType, String authToken) {
        this(null, name, url, location, sourceType, authToken);
    }

    public ThermostatDTO toDto() {
        return new ThermostatDTO(id, location.getId(), name, url);
    }

}
