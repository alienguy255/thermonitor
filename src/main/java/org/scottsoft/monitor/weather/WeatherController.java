package org.scottsoft.monitor.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{locationId}/samples", method = RequestMethod.GET)
    public ResponseEntity<List<WeatherSampleDTO>> getThermostatSamples(@PathVariable(value = "locationId") String locationId, @RequestParam(value = "fromTime", required = true) long fromTime, @RequestParam(value = "toTime", required = true) long toTime) {
        try {
            Date startTime = new Date(fromTime);
            Date endTime = new Date(toTime);
            log.debug("Requesting weather data for location id {} from {} to {}", locationId, new Date(fromTime), new Date(toTime));

            List<IWeatherSample> samples = weatherService.getSamples(UUID.fromString(locationId), startTime, endTime);
            List<WeatherSampleDTO> weatherDTOs = samples.stream().map(weatherSample -> new WeatherSampleDTO(weatherSample.getLocationId(), weatherSample.getCurrentTemp(), weatherSample.getTime())).collect(Collectors.toList());
            return new ResponseEntity<>(weatherDTOs, HttpStatus.OK);
        } catch(Exception e) {
            // TODO: throw new RestException, import and add dependency
            throw new IllegalStateException("Error getting weather samples.", e);
        }
    }

}

