package org.scottsoft.monitor.domain.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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

}
