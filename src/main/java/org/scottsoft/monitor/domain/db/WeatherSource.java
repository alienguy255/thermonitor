package org.scottsoft.monitor.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "weather_source")
public class WeatherSource implements IWeatherSource {

    @Id
    @Column
    private String id;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "url")
    private String url;

    @Column(name = "api_target")
    @Enumerated(EnumType.STRING)
    private WeatherSourceAPITarget apiTarget;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public WeatherSourceAPITarget getApiTarget() {
        return apiTarget;
    }

    public void setApiTarget(WeatherSourceAPITarget apiTarget) {
        this.apiTarget = apiTarget;
    }

}
