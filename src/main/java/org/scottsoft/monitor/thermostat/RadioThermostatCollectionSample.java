package org.scottsoft.monitor.thermostat;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class RadioThermostatCollectionSample implements TStatCollectionSample {

    private Double temp;
    private Double t_heat;
    private Double tstate;

    @Override
    public Double temp() {
        return temp;
    }

    @Override
    public Double targetTemp() {
        return t_heat;
    }

    @Override
    public Double tstate() {
        return tstate;
    }
}
