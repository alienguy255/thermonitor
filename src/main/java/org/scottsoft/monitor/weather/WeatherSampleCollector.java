package org.scottsoft.monitor.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.common.MonitorAsyncTaskRunner;
import org.scottsoft.monitor.location.Location;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherSampleCollector extends MonitorAsyncTaskRunner<WeatherSource, OpenWeatherSample> {

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
    private final SimpMessagingTemplate template;

    @Override
    protected OpenWeatherSample collectSample(WeatherSource weatherSource) {
        Location location = weatherSource.getLocation();
        log.debug("Retrieving weather sample for location {}, longitude {}, latitude {}", location.getDescription(), location.getLongitude(), location.getLatitude());

        OpenWeatherSample response = restTemplate.getForObject(weatherSource.getUrl(), OpenWeatherSample.class);
        log.debug("response from open weather: " + Optional.ofNullable(response).map(OpenWeatherSample::getMain).orElse(null));
        return response;
    }

    @Override
    protected void insertSample(WeatherSource weatherSource, OpenWeatherSample response) {
        weatherService.insertSample(weatherSource.getLocation().getId(), response.getTemp(), new Date().getTime());
    }

    @Override
    protected void notifyClients(WeatherSource weatherSource, OpenWeatherSample response) {
        OldWeatherSampleDTO sampleDTO = new OldWeatherSampleDTO(weatherSource.getId(), response.getTemp(), new Date());
        template.convertAndSend("/topic/weather-updates", sampleDTO);
    }

}
