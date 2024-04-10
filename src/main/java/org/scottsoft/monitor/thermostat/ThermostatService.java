package org.scottsoft.monitor.thermostat;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.scottsoft.monitor.location.Location;
import org.scottsoft.monitor.location.LocationRepository;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThermostatService {

    private final ThermostatRepository thermostatRepository;
    private final LocationRepository locationRepository;
	private final ThermostatSampleRrdDAO thermostatSampleDAO;
	
	public List<Thermostat> getAllThermostats() {
        return StreamSupport.stream(thermostatRepository.findAll().spliterator(), false).toList();
    }

    public List<Thermostat> getThermostatsByLocation(Location location) {
        return thermostatRepository.findByLocation(location);
    }

	public List<IThermostatSample> getThermostatSamples(UUID thermostatId, Date fromTime, Date toTime) {
		return thermostatSampleDAO.getSamples(thermostatId, fromTime, toTime);
	}

    public Thermostat createThermostat(String name, String url, UUID locationId) {
        return locationRepository.findById(locationId)
                .map(location -> thermostatRepository.save(new Thermostat(name, url, location)))
                .orElseThrow(() -> new IllegalArgumentException("The provided location id was not found."));
    }

	public void insertThermostatSample(UUID thermostatId, double currentTemp, double tmode, double override, double targetTemp, double tstate, long timeMs) {
        RrdSample rrdSample = new RrdSample(timeMs);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_CURRENT_TEMP, currentTemp);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TMODE, tmode);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_OVERRIDE, override);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TARGET_TEMP, targetTemp);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TSTATE, tstate);
        thermostatSampleDAO.insertSample(thermostatId, rrdSample);
    }
	
}
