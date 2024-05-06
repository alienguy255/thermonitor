package org.scottsoft.monitor.thermostat;

import lombok.RequiredArgsConstructor;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Sample;
import org.scottsoft.monitor.common.RrdSample;
import org.scottsoft.monitor.common.ISampleRrdDb;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ThermostatSampleRrdDb implements ISampleRrdDb, Closeable {

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";
    public static final String DS_NAME_TARGET_TEMP = "targetTemp";
    public static final String DS_NAME_TMODE = "tmode";
    public static final String DS_NAME_OVERRIDE = "override";
    public static final String DS_NAME_TSTATE = "tstate";

    private final RrdDb rrdDb;

    public void insertSample(RrdSample rrdSample) throws IOException {
        Sample sample = rrdDb.createSample();
        for (String dsName : rrdDb.getDsNames()) {
            sample.setValue(dsName, Objects.requireNonNullElse(rrdSample.getMetric(dsName), Double.NaN));
        }

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
