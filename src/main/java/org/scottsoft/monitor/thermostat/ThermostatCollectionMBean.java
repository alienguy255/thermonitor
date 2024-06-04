package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@ManagedResource
@RequiredArgsConstructor
public class ThermostatCollectionMBean {

    private final RestTemplate restTemplate;

    @ManagedOperation
    public void testCollection(String url, String authToken, String sourceType) {
        HttpHeaders headers = new HttpHeaders();
        if (authToken != null) {
            headers.set("Authorization", "Bearer " + authToken);
        }

        ResponseEntity<? extends TStatCollectionSample> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ThermostatSourceType.valueOf(sourceType).getResponseType());

        log.info("Response from thermostat: {}", response.getBody());
    }

}
