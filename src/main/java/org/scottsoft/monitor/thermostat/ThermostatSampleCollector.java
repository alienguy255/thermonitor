package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.common.MonitorAsyncTaskRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThermostatSampleCollector extends MonitorAsyncTaskRunner<Thermostat, TStatSample> {

    private final RestTemplate restTemplate;
    private final ThermostatService thermostatService;
    private final SimpMessagingTemplate template;

    @Override
    protected TStatSample collectSample(Thermostat thermostat) {
        log.debug("Retrieving sample from thermostat {} with url {}", thermostat.getName(), thermostat.getUrl());

        // NOTE: timeouts configured in RestTemplateConfig
        TStatSample response = restTemplate.getForObject(thermostat.getUrl(), TStatSample.class);
        log.debug("Response from thermostat {} = {}", thermostat.getName(), response);
        return response;
    }

    @Override
    protected void insertSample(Thermostat thermostat, TStatSample response) {
        // insert sample into database:
        thermostatService.insertThermostatSample(thermostat.getId(), Double.parseDouble(response.temp()), Double.parseDouble(response.tmode()),
                Double.parseDouble(response.override()), Double.parseDouble(response.t_heat()), Double.parseDouble(response.tstate()), new Date().getTime());
    }

    @Override
    protected void notifyClients(Thermostat thermostat, TStatSample response) {
        ThermostatSampleDTO sampleDTO = new ThermostatSampleDTO(thermostat.getId(), Double.parseDouble(response.temp()), Double.parseDouble(response.override()),
                Double.parseDouble(response.t_heat()), Double.parseDouble(response.tstate()), new Date().getTime());
        template.convertAndSend("/topic/tstat-updates", sampleDTO);
    }
}
