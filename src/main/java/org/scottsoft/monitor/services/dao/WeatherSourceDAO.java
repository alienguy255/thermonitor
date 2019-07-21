package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.db.WeatherSource;
import org.scottsoft.monitor.repositories.WeatherSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherSourceDAO implements IWeatherSourceDAO {

    @Autowired
    private WeatherSourceRepository weatherSourceRepository;

    @Override
    public Iterable<WeatherSource> getAllWeatherSources() {
        return weatherSourceRepository.findAll();
    }
}
