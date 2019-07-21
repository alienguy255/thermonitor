package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.db.IWeatherSource;
import org.scottsoft.monitor.domain.db.WeatherSource;

public interface IWeatherSourceDAO {

    Iterable<WeatherSource> getAllWeatherSources();

}
