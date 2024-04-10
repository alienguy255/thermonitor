package org.scottsoft.monitor.weather;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {

	private final WeatherSourceRepository weatherSourceRepository;
	private final WeatherSampleRrdDAO weatherSampleDAO;

	public List<WeatherSource> getAllWeatherSources() {
		return StreamSupport.stream(weatherSourceRepository.findAll().spliterator(), false).toList();
	}

	public List<IWeatherSample> getSamples(UUID locationId, Date fromTime, Date toTime) {
		return weatherSampleDAO.getSamples(locationId, fromTime, toTime);
	}
	
	public void insertSample(UUID locationId, double currentTemp, Date time) {
        RrdSample rrdSample = new RrdSample(time.getTime());
        rrdSample.addMetric(WeatherSampleRrdDb.DS_NAME_CURRENT_TEMP, currentTemp);
        weatherSampleDAO.insertSample(locationId, rrdSample);
	}
	
}
