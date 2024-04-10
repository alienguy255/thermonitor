package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ThermostatSampleCollectorJob {

    private final ThermostatService thermostatService;
    private final ThermostatSampleCollector thermostatSampleCollector;

    @Scheduled(cron="0/60 * * * * ?")
    public void pollAllDevices() {
        thermostatService.getAllThermostats().forEach(thermostatSampleCollector::run);
    }

}
