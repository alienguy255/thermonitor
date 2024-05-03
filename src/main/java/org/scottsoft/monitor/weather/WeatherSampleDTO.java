package org.scottsoft.monitor.weather;

public record WeatherSampleDTO(Double currentTemp, Long timeMs) {

    public WeatherSampleDTO(Double currentTemp, Long timeMs) {
        this.currentTemp = currentTemp.isNaN() ? null : currentTemp;
        this.timeMs = timeMs;
    }

}
