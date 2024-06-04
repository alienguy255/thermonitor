package org.scottsoft.monitor.thermostat;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class HAThermostatCollectionSample implements TStatCollectionSample {

    private static final String CURRENT_TEMP = "current_temperature";
    private static final String TARGET_TEMP = "temperature";
    private static final String HEAT_MODE = "heat";
    private static final String HVAC_ACTION = "hvac_action";
    private static final String HEATING_ACTION = "heating";

    private String state;
    private Map<String, Object> attributes;

    @Override
    public Double temp() {
        return Double.valueOf(attributes.get(CURRENT_TEMP).toString());
    }

    @Override
    public Double tmode() {
        return HEAT_MODE.equals(state) ? 1.0 : 0.0;
    }

    @Override
    public Double override() {
        // not reported by this thermostat, just return 0
        return 0.0;
    }

    @Override
    public Double targetTemp() {
        return Double.valueOf(attributes.get(TARGET_TEMP).toString());
    }

    @Override
    public Double tstate() {
        // hvac_action is "idle" when not running
        return attributes.get(HVAC_ACTION).equals(HEATING_ACTION) ? 1.0 : 0.0;
    }
}
