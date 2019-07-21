package org.scottsoft.monitor.services;

import org.scottsoft.monitor.domain.db.WeatherSource;
import org.scottsoft.monitor.services.dao.IWeatherSourceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherSourceService {

    @Autowired
    private IWeatherSourceDAO weatherSourceDAO;

    public Iterable<WeatherSource> getAllWeatherSources() {
        return weatherSourceDAO.getAllWeatherSources();
    }

}
