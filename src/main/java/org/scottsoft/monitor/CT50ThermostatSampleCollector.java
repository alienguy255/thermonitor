package org.scottsoft.monitor;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.domain.db.IThermostat;
import org.scottsoft.monitor.domain.dto.ThermostatSampleDTO;
import org.scottsoft.monitor.domain.interaction.filtrete.TStatSample;
import org.scottsoft.monitor.services.ThermostatService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@Component
public class CT50ThermostatSampleCollector extends MonitorAsyncTaskRunner<IThermostat, TStatSample> {

    private RestTemplate restTemplate;

    private ThermostatService thermostatService;

    private SimpMessagingTemplate template;

    public CT50ThermostatSampleCollector(RestTemplate restTemplate, ThermostatService thermostatService,
                                         SimpMessagingTemplate template) {
        this.restTemplate = restTemplate;
        this.thermostatService = thermostatService;
        this.template = template;
    }

    @Override
    protected TStatSample collectSample(IThermostat thermostat) {
        log.info("Retrieving sample from thermostat {} with ip {}", thermostat.getName(), thermostat.getIpAddress());

        // query the thermostat for tstat sample:
        String thermostatURL = MessageFormat.format("http://{0}/tstat", thermostat.getIpAddress());

        TStatSample response = restTemplate.getForObject(thermostatURL, TStatSample.class);
        log.info("Response from thermostat {} = {}", thermostat.getName(), response);
        return response;
    }

    @Override
    protected void insertSample(IThermostat thermostat, TStatSample response) {
        // insert sample into database:
        thermostatService.insertThermostatSample(thermostat.getId(), Double.valueOf(response.getTemp()), Double.valueOf(response.getTmode()),
                Double.valueOf(response.getOverride()), Double.valueOf(response.getT_heat()), Double.valueOf(response.getTstate()), new Date());
    }

    @Override
    protected void notifyClients(IThermostat thermostat, TStatSample response) {
        ThermostatSampleDTO sampleDTO = new ThermostatSampleDTO(thermostat.getId(), Double.valueOf(response.getTemp()), Double.valueOf(response.getOverride()),
                Double.valueOf(response.getT_heat()), Double.valueOf(response.getTstate()), new Date().getTime());
        template.convertAndSend("/topic/tstat-updates", sampleDTO);
    }
}
