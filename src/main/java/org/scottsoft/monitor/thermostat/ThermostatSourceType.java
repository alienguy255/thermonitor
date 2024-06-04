package org.scottsoft.monitor.thermostat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThermostatSourceType {

    RADIO_THERMOSTAT(RadioThermostatCollectionSample.class),

    HOME_ASSISTANT(HAThermostatCollectionSample.class);

    private final Class<? extends TStatCollectionSample> responseType;

}
