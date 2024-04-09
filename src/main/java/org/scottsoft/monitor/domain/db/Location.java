package org.scottsoft.monitor.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.scottsoft.monitor.domain.dto.LocationDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location implements ILocation {

    @Id
    @Column
    private String id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    public LocationDTO toDto() {
        return new LocationDTO(id, zipCode, city, state);
    }

}
