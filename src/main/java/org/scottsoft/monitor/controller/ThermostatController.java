package org.scottsoft.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.domain.db.Thermostat;
import org.scottsoft.monitor.domain.dto.ThermostatDTO;
import org.scottsoft.monitor.domain.dto.ThermostatSampleDTO;
import org.scottsoft.monitor.domain.sample.IThermostatSample;
import org.scottsoft.monitor.services.ThermostatService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/thermostats")
public class ThermostatController {

    @Autowired
    private ThermostatService thermostatService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ThermostatDTO>> getThermostats() {
        Iterable<Thermostat> allThermostats = thermostatService.getAllThermostats();
        List<ThermostatDTO> thermostatDTOs = StreamSupport.stream(allThermostats.spliterator(), false).map(thermostat -> new ThermostatDTO(thermostat.getId(),
                thermostat.getLocation(), thermostat.getName(), thermostat.getIpAddress())).collect(Collectors.toList());

        return new ResponseEntity<>(thermostatDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/samples", method = RequestMethod.GET)
    public ResponseEntity<List<ThermostatSampleDTO>> getThermostatSamples(@PathVariable(value = "id") String thermostatId, @RequestParam(value = "fromTime") long fromTime, @RequestParam(value = "toTime") long toTime) {
        try {
            Date startTime = new Date(fromTime);
            Date endTime = new Date(toTime);
            log.debug("Requesting tstat data for thermostat id {} from {} to {}", thermostatId, new Date(fromTime), new Date(toTime));

            List<IThermostatSample> thermostatSamples = thermostatService.getThermostatSamples(thermostatId, startTime, endTime);
            List<ThermostatSampleDTO> thermostatSampleDTOs = thermostatSamples.stream().map(thermostatSample -> new ThermostatSampleDTO(thermostatSample.getThermostatId(),
                    thermostatSample.getCurrentTemp(), thermostatSample.getOverride(), thermostatSample.getTargetTemp(), thermostatSample.getTstate(), thermostatSample.getTime().getTime())).collect(Collectors.toList());

            return new ResponseEntity<>(thermostatSampleDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: throw new RestException, import and add dependency
            throw new IllegalStateException("Error getting thermostat samples.", e);
        }
    }

}
