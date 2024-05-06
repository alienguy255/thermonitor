package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import org.scottsoft.monitor.location.LocationDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ThermostatGraphQLController {

    private final ThermostatService thermostatService;

    @QueryMapping
    public List<ThermostatDTO> thermostats() {
        return thermostatService.getAllThermostats().stream()
                .map(Thermostat::toDto)
                .toList();
    }

    @QueryMapping
    public ThermostatDTO thermostatById(@Argument String id) {
        Thermostat thermostat = thermostatService.getThermostatById(UUID.fromString(id));
        return Optional.ofNullable(thermostat).map(Thermostat::toDto).orElse(null);
    }

    @SchemaMapping
    public List<ThermostatDTO> thermostats(LocationDTO location) {
        return thermostatService.getThermostatsByLocationId(location.id()).stream()
                .map(Thermostat::toDto)
                .toList();
    }

    @SchemaMapping
    public List<ThermostatSampleDTO> samples(ThermostatDTO thermostat, @Argument long fromTimeMs, @Argument long toTimeMs) {
        List<IThermostatSample> thermostatSamples = thermostatService.getThermostatSamples(thermostat.id(), fromTimeMs, toTimeMs);
        return thermostatSamples.stream()
                .map(ts -> new ThermostatSampleDTO(ts.currentTemp(), ts.override(), ts.targetTemp(), ts.tstate(), ts.time().getTime()))
                .toList();
    }

}
