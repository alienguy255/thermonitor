package org.scottsoft.monitor.weather;

import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Sample;
import org.scottsoft.monitor.common.ISampleRrdDb;
import org.scottsoft.monitor.common.RrdSample;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WeatherSampleRrdDb implements ISampleRrdDb {

    private final RrdDb rrdDb;

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";

    public WeatherSampleRrdDb(RrdDb rrdDb) {
        this.rrdDb = rrdDb;
    }

    public void insertSample(RrdSample rrdSample) {
        try {
            Sample sample = rrdDb.createSample();
            sample.setValue(DS_NAME_CURRENT_TEMP, rrdSample.getMetric(DS_NAME_CURRENT_TEMP));

            sample.setTime(TimeUnit.MILLISECONDS.toSeconds(rrdSample.getTimestamp()));
            sample.update();

            rrdDb.close();
        } catch(IOException e) {
            throw new IllegalStateException("Error constructing and adding thermostat sample to RRD " + rrdDb.getPath(), e);
        }
    }

}
