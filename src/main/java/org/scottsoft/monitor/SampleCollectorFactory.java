package org.scottsoft.monitor;

import org.scottsoft.monitor.domain.db.IThermostat;
import org.scottsoft.monitor.domain.db.IWeatherSource;
import org.scottsoft.monitor.domain.db.ThermostatModel;
import org.scottsoft.monitor.domain.db.WeatherSourceAPITarget;
import org.scottsoft.monitor.domain.interaction.filtrete.TStatSample;
import org.scottsoft.monitor.domain.interaction.openweather.OpenWeatherSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleCollectorFactory {

    @Autowired
    private CT50ThermostatSampleCollector thermostatSampleCollector;

    @Autowired
    private MockThermostatSampleCollector mockThermostatSampleCollector;

    @Autowired
    private OpenWeatherSampleCollector weatherSampleCollector;

    @Autowired
    private MockWeatherSampleCollector mockWeatherSampleCollector;

    public MonitorAsyncTaskRunner<IThermostat, TStatSample> getThermostatSampleCollector(ThermostatModel thermostatModel) {
        if (ThermostatModel.CT50.equals(thermostatModel)) {
            return thermostatSampleCollector;
        } else if (ThermostatModel.MOCK.equals(thermostatModel)) {
            return mockThermostatSampleCollector;
        } else {
            throw new IllegalStateException("Cannot create thermostat sample collector for model " + thermostatModel);
        }
    }

    public MonitorAsyncTaskRunner<IWeatherSource, OpenWeatherSample> getWeatherSampleCollector(WeatherSourceAPITarget apiTarget) {
        if (WeatherSourceAPITarget.OPEN_WEATHER_MAP.equals(apiTarget)) {
            return weatherSampleCollector;
        } else if (WeatherSourceAPITarget.MOCK.equals(apiTarget)) {
            return mockWeatherSampleCollector;
        } else {
            throw new IllegalStateException("Cannot create weather sample collector for target type " + apiTarget);
        }
    }

}
