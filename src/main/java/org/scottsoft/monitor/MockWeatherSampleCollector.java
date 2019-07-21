package org.scottsoft.monitor;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.domain.db.ILocation;
import org.scottsoft.monitor.domain.db.IWeatherSource;
import org.scottsoft.monitor.domain.dto.WeatherSampleDTO;
import org.scottsoft.monitor.domain.interaction.openweather.OpenWeatherMainSample;
import org.scottsoft.monitor.domain.interaction.openweather.OpenWeatherSample;
import org.scottsoft.monitor.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MockWeatherSampleCollector extends MonitorAsyncTaskRunner<IWeatherSource, OpenWeatherSample> {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    protected OpenWeatherSample collectSample(IWeatherSource weatherSource) {
        ILocation location = weatherSource.getLocation();

        log.info("Retrieving mock weather sample for zipCode {}, city {}, state {}", location.getZipCode(), location.getCity(), location.getState());
        OpenWeatherMainSample mainSample = new OpenWeatherMainSample();
        mainSample.setTemp("85.5");
        OpenWeatherSample response = new OpenWeatherSample();
        response.setMain(mainSample);
        return response;
    }

    @Override
    protected void insertSample(IWeatherSource weatherSource, OpenWeatherSample response) {
        weatherService.insertSample(weatherSource.getLocation().getId(), response.getTemp(), new Date());
    }

    @Override
    protected void notifyClients(IWeatherSource weatherSource, OpenWeatherSample response) {
        WeatherSampleDTO sampleDTO = new WeatherSampleDTO(weatherSource.getId(), Double.valueOf(response.getTemp()), new Date());
        template.convertAndSend("/topic/weather-updates", sampleDTO);
    }

}
