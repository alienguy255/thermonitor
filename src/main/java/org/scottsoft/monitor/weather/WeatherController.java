package org.scottsoft.monitor.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @RequestMapping(value = "/{locationId}/samples", method = RequestMethod.GET)
    public ResponseEntity<List<OldWeatherSampleDTO>> getWeatherSamples(@PathVariable(value = "locationId") String locationId, @RequestParam(value = "fromTime", required = true) long fromTimeMs, @RequestParam(value = "toTime", required = true) long toTimeMs) {
        try {
            log.debug("Requesting weather data for location id {} from {} to {}", locationId, new Date(fromTimeMs), new Date(toTimeMs));

            List<IWeatherSample> samples = weatherService.getSamples(UUID.fromString(locationId), fromTimeMs, toTimeMs);
            List<OldWeatherSampleDTO> weatherDTOs = samples.stream().map(weatherSample -> new OldWeatherSampleDTO(weatherSample.getLocationId(), weatherSample.getCurrentTemp(), weatherSample.getTime())).collect(Collectors.toList());
            return new ResponseEntity<>(weatherDTOs, HttpStatus.OK);
        } catch(Exception e) {
            // TODO: throw new RestException, import and add dependency
            throw new IllegalStateException("Error getting weather samples.", e);
        }
    }

}

