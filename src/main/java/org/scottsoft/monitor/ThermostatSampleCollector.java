package org.scottsoft.monitor;

import org.scottsoft.monitor.services.ThermostatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ThermostatSampleCollector {

    @Autowired
    private SampleCollectorFactory sampleCollectorFactory;

    @Autowired
    private ThermostatService thermostatService;

    @Scheduled(cron="0/60 * * * * ?")
    public void pollAllDevices() {
        thermostatService.getAllThermostats().forEach(thermostat -> sampleCollectorFactory.getThermostatSampleCollector(thermostat.getModel()).run(thermostat));
    }

}
