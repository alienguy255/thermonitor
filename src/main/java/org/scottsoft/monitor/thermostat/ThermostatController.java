package org.scottsoft.monitor.thermostat;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/thermostats")
public class ThermostatController {

    private final ThermostatService thermostatService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ThermostatDTO>> getAllThermostats() {
        List<ThermostatDTO> tstatRecords = thermostatService.getAllThermostats().stream()
                .map(Thermostat::toDto)
                .toList();

        return new ResponseEntity<>(tstatRecords, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/samples", method = RequestMethod.GET)
    public ResponseEntity<List<ThermostatSampleDTO>> getThermostatSamples(@PathVariable(value = "id") String thermostatId, @RequestParam(value = "fromTime") long fromTime, @RequestParam(value = "toTime") long toTime) {
        try {
            Date startTime = new Date(fromTime);
            Date endTime = new Date(toTime);
            log.debug("Requesting tstat data for thermostat id {} from {} to {}", thermostatId, new Date(fromTime), new Date(toTime));

            List<IThermostatSample> thermostatSamples = thermostatService.getThermostatSamples(UUID.fromString(thermostatId), startTime, endTime);
            List<ThermostatSampleDTO> thermostatSampleDTOs = thermostatSamples.stream().map(thermostatSample -> new ThermostatSampleDTO(thermostatSample.thermostatId(),
                    thermostatSample.currentTemp(), thermostatSample.override(), thermostatSample.targetTemp(), thermostatSample.tstate(), thermostatSample.time().getTime())).collect(Collectors.toList());

            return new ResponseEntity<>(thermostatSampleDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // log but dont propagate stack to client, instead log it and return 500 to user
            // TODO: there could be cases where user input is invalid and we would want to return a 400 for those
            log.warn("An error occurred fetching thermostat samples.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private record TStatRequestBody(String name, String url, UUID locationId) {}

    @RequestMapping(method = RequestMethod.POST)
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
