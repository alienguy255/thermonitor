package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.common.MonitorAsyncTaskRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThermostatSampleCollector extends MonitorAsyncTaskRunner<Thermostat, TStatCollectionSample> {

    private final RestTemplate restTemplate;
    private final ThermostatService thermostatService;
    private final SimpMessagingTemplate template;

    @Override
    protected TStatCollectionSample collectSample(Thermostat thermostat) {
        log.debug("Retrieving sample from thermostat {} with url {}", thermostat.getName(), thermostat.getUrl());

        HttpHeaders headers = new HttpHeaders();
        if (thermostat.getAuthToken() != null) {
            headers.set("Authorization", "Bearer " + thermostat.getAuthToken());
        }

        // NOTE: timeouts configured in RestTemplateConfig
        ResponseEntity<? extends TStatCollectionSample> response = restTemplate.exchange(
                thermostat.getUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                thermostat.getSourceType().getResponseType());

        log.debug("Response from thermostat {} = {}", thermostat.getName(), response.getBody());
        return response.getBody();
    }

    @Override
    protected void insertSample(Thermostat thermostat, TStatCollectionSample response, long collectionTimeMs) {
        thermostatService.insertSample(thermostat.getId(), response.temp(), response.tmode(), response.override(), response.targetTemp(), response.tstate(), collectionTimeMs);
    }

    @Override
    protected void notifyClients(Thermostat thermostat, TStatCollectionSample response, long collectionTimeMs) {
        ThermostatUpdateEvent tstatUpdate = new ThermostatUpdateEvent(thermostat.getId(), new ThermostatSampleDTO(response.temp(), response.targetTemp(), response.tstate(), collectionTimeMs));
        template.convertAndSend("/topic/tstat-updates", tstatUpdate);
    }
}
