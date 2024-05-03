package org.scottsoft.monitor.weather;

import lombok.RequiredArgsConstructor;
import org.scottsoft.monitor.location.LocationDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherSampleGraphQLController {

    private final WeatherService weatherService;

    @SchemaMapping
    public List<WeatherSampleDTO> weatherSamples(LocationDTO location, @Argument long fromTimeMs, @Argument long toTimeMs) {
        return weatherService.getSamples(location.id(), fromTimeMs, toTimeMs).stream()
                .map(weatherSample -> new WeatherSampleDTO(weatherSample.getCurrentTemp(), weatherSample.getTime().getTime()))
                .toList();
    }

}
