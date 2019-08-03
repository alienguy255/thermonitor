package org.scottsoft.monitor;

import lombok.RequiredArgsConstructor;
import org.scottsoft.monitor.services.ThermostatService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ThermostatSampleCollector {

    private final SampleCollectorFactory sampleCollectorFactory;

    private final ThermostatService thermostatService;

    @Scheduled(cron="0/60 * * * * ?")
    public void pollAllDevices() {
        thermostatService.getAllThermostats().forEach(thermostat -> sampleCollectorFactory.getThermostatSampleCollector(thermostat.getModel()).run(thermostat));
    }

}
