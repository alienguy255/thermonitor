package org.scottsoft.monitor;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.domain.db.IThermostat;
import org.scottsoft.monitor.domain.dto.ThermostatSampleDTO;
import org.scottsoft.monitor.domain.interaction.filtrete.TStatSample;
import org.scottsoft.monitor.services.ThermostatService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MockThermostatSampleCollector extends MonitorAsyncTaskRunner<IThermostat, TStatSample> {

    private ThermostatService thermostatService;

    private SimpMessagingTemplate template;

    public MockThermostatSampleCollector(ThermostatService thermostatService, SimpMessagingTemplate template) {
        this.thermostatService = thermostatService;
        this.template = template;
    }

    @Override
    protected TStatSample collectSample(IThermostat thermostat) {
        log.info("Retrieving mock sample from thermostat {} with ip {}", thermostat.getName(), thermostat.getIpAddress());
        TStatSample response = new TStatSample();

        response.setTemp(Double.toString(Math.random() * 100));
        response.setTmode("1");
        response.setFmode("0");
        response.setOverride("0");
        response.setT_heat("45.00");
        response.setTstate((Math.random() * 1) > 0.5D ? "0" : "1");
        response.setFstate("0");
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
