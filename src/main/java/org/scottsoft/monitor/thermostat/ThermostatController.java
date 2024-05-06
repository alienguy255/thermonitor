package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/thermostats")
public class ThermostatController {

    private final ThermostatService thermostatService;

    @GetMapping
    public ResponseEntity<List<ThermostatDTO>> getAllThermostats() {
        List<ThermostatDTO> tstatRecords = thermostatService.getAllThermostats().stream()
                .map(Thermostat::toDto)
                .toList();

        return new ResponseEntity<>(tstatRecords, HttpStatus.OK);
    }

    @GetMapping(params = "locationId")
    public ResponseEntity<List<ThermostatDTO>> getThermostatsByLocation(@RequestParam(value = "locationId") String locationId) {
        List<ThermostatDTO> tstatRecords = thermostatService.getThermostatsByLocationId(UUID.fromString(locationId)).stream()
                .map(Thermostat::toDto)
                .toList();

        return new ResponseEntity<>(tstatRecords, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/samples")
    public ResponseEntity<ThermostatSampleDTOList> getSamples(@PathVariable(value = "id") String thermostatId, @RequestParam(value = "fromTime") long fromTimeMs, @RequestParam(value = "toTime") long toTimeMs) {
        log.debug("Requesting tstat data for thermostat id {} from {} to {}", thermostatId, new Date(fromTimeMs), new Date(toTimeMs));

        List<IThermostatSample> thermostatSamples = thermostatService.getThermostatSamples(UUID.fromString(thermostatId), fromTimeMs, toTimeMs);
        List<ThermostatSampleDTO> tstatSamples = thermostatSamples.stream()
                .map(ts -> new ThermostatSampleDTO(ts.currentTemp(), ts.override(), ts.targetTemp(), ts.tstate(), ts.time().getTime()))
                .toList();

        return new ResponseEntity<>(new ThermostatSampleDTOList(UUID.fromString(thermostatId), tstatSamples), HttpStatus.OK);
    }

    public record TStatRequestBody(String name, String url, UUID locationId) {}

    @PostMapping
    public ResponseEntity<?> createThermostat(@RequestBody TStatRequestBody tstatBody) {
        try {
            return new ResponseEntity<>(thermostatService.createThermostat(tstatBody.name(), tstatBody.url(), tstatBody.locationId()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // log but dont propagate stack to client, instead log it and return 400 to user
            log.warn("An error occurred creating thermostat.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
