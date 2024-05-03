package org.scottsoft.monitor.common;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class RrdSample {

    private final long timestamp;

    private final Map<String, Double> metrics = Maps.newHashMap();

    public RrdSample(long timestamp) {
        this.timestamp = timestamp;
    }

    public RrdSample(long timestamp, Map<String, Double> metrics) {
        this(timestamp);
        this.metrics.putAll(metrics);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void addMetric(String counterName, Double value) {
        metrics.put(counterName, value);
    }

    public Double getMetric(String counterName) {
        return metrics.get(counterName);
    }

    public Map<String, Double> getMetrics() {
        return Collections.unmodifiableMap(metrics);
    }

}
