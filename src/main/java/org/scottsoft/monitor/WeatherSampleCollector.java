package org.scottsoft.monitor;

import org.scottsoft.monitor.services.WeatherSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class WeatherSampleCollector {

    @Autowired
    private SampleCollectorFactory sampleCollectorFactory;

    @Autowired
    private WeatherSourceService weatherSourceService;

    @Scheduled(cron="0 0/60 * * * ?")
    public void pollWeatherLocations() {
        weatherSourceService.getAllWeatherSources().forEach(weatherSource -> sampleCollectorFactory.getWeatherSampleCollector(weatherSource.getApiTarget()).run(weatherSource));
    }

}
