package org.scottsoft.monitor.weather;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final WeatherSourceRepository weatherSourceRepository;
	private final WeatherSampleRepository weatherSampleRepository;

	public List<WeatherSource> getAllWeatherSources() {
		return StreamSupport.stream(weatherSourceRepository.findAll().spliterator(), false).toList();
	}

	public List<IWeatherSample> getSamples(UUID locationId, long fromTimeMs, long toTimeMs) {
		return weatherSampleRepository.getSamples(locationId, fromTimeMs, toTimeMs);
	}
	
	public void insertSample(UUID locationId, double currentTemp, long timeMs) {
        RrdSample rrdSample = new RrdSample(timeMs);
        rrdSample.addMetric(WeatherSampleRrdDb.DS_NAME_CURRENT_TEMP, currentTemp);
        weatherSampleRepository.insertSample(locationId, rrdSample);
	}
	
}
