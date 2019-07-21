package org.scottsoft.monitor.services;

import java.util.Date;
import java.util.List;

import org.scottsoft.monitor.domain.db.Location;
import org.scottsoft.monitor.repositories.ThermostatRepository;
import org.scottsoft.monitor.services.dao.ISampleDAO;
import org.scottsoft.monitor.services.dao.ThermostatSampleRrdDAO;
import org.scottsoft.monitor.services.dao.ThermostatSampleRrdDb;
import org.scottsoft.monitor.domain.db.Thermostat;
import org.scottsoft.monitor.domain.sample.IThermostatSample;
import org.scottsoft.monitor.domain.sample.RrdSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ThermostatService {

    @Autowired
    private ThermostatRepository thermostatRepository;

	@Autowired
    @Qualifier(ThermostatSampleRrdDAO.BEAN_ID)
	private ISampleDAO<IThermostatSample> thermostatSampleDAO;

	public Thermostat getThermostat(String thermostatId) {
	    return thermostatRepository.findById(thermostatId).orElse(null);
    }
	
	public Iterable<Thermostat> getAllThermostats() {
        return thermostatRepository.findAll();
    }

    public Iterable<Thermostat> getThermostatsByLocation(Location location) {
        return thermostatRepository.findByLocation(location);
    }

	public List<IThermostatSample> getThermostatSamples(String thermostatId, Date fromTime, Date toTime) {
		return thermostatSampleDAO.getSamples(thermostatId, fromTime, toTime);
	}
	
	public void insertThermostatSample(String thermostatId, double currentTemp, double tmode, double override, double targetTemp, double tstate, Date time) {
        RrdSample rrdSample = new RrdSample(time.getTime());
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_CURRENT_TEMP, currentTemp);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TMODE, tmode);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_OVERRIDE, override);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TARGET_TEMP, targetTemp);
        rrdSample.addMetric(ThermostatSampleRrdDb.DS_NAME_TSTATE, tstate);
        thermostatSampleDAO.insertSample(thermostatId, rrdSample);
    }
	
}
