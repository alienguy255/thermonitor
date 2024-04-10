package org.scottsoft.monitor.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WeatherSampleCollectorJob {

    private final WeatherService weatherService;
    private final WeatherSampleCollector weatherSampleCollector;

    @Scheduled(cron="0 0/60 * * * ?")
    public void pollWeatherLocations() {
        weatherService.getAllWeatherSources().forEach(weatherSampleCollector::run);
    }

}
