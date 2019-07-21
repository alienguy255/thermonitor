package org.scottsoft.monitor.services;

import java.util.Date;
import java.util.List;

import org.scottsoft.monitor.domain.sample.IWeatherSample;
import org.scottsoft.monitor.domain.sample.RrdSample;
import org.scottsoft.monitor.services.dao.ISampleDAO;
import org.scottsoft.monitor.services.dao.WeatherSampleRrdDAO;
import org.scottsoft.monitor.services.dao.WeatherSampleRrdDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	@Autowired
    @Qualifier(WeatherSampleRrdDAO.BEAN_ID)
	private ISampleDAO<IWeatherSample> weatherSampleDAO;
	
	public List<IWeatherSample> getSamples(String locationId, Date fromTime, Date toTime) {
		return weatherSampleDAO.getSamples(locationId, fromTime, toTime);
	}
	
	public void insertSample(String locationId, double currentTemp, Date time) {
        RrdSample rrdSample = new RrdSample(time.getTime());
        rrdSample.addMetric(WeatherSampleRrdDb.DS_NAME_CURRENT_TEMP, currentTemp);
        weatherSampleDAO.insertSample(locationId, rrdSample);
	}
	
}
