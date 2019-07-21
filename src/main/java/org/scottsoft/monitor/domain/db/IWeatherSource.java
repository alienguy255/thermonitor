package org.scottsoft.monitor.domain.db;

public interface IWeatherSource {

    String getId();

    Location getLocation();

    String getUrl();

    WeatherSourceAPITarget getApiTarget();

}
