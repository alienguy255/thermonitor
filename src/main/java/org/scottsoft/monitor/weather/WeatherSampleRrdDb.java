package org.scottsoft.monitor.weather;

import lombok.RequiredArgsConstructor;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Sample;
import org.scottsoft.monitor.common.ISampleRrdDb;
import org.scottsoft.monitor.common.RrdSample;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class WeatherSampleRrdDb implements ISampleRrdDb, Closeable {

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";

    private final RrdDb rrdDb;

    public void insertSample(RrdSample rrdSample) throws IOException {
        Sample sample = rrdDb.createSample();
        sample.setValue(DS_NAME_CURRENT_TEMP, rrdSample.getMetric(DS_NAME_CURRENT_TEMP));

        sample.setTime(TimeUnit.MILLISECONDS.toSeconds(rrdSample.getTimestamp()));
        sample.update();
    }

    @Override
    public void close() throws IOException {
        if (rrdDb != null) {
            rrdDb.close();
        }
    }

}
