package org.scottsoft.monitor.services.dao;

import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Sample;
import org.scottsoft.monitor.domain.sample.RrdSample;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ThermostatSampleRrdDb implements ISampleRrdDb {

    private final RrdDb rrdDb;

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";
    public static final String DS_NAME_TARGET_TEMP = "targetTemp";
    public static final String DS_NAME_TMODE = "tmode";
    public static final String DS_NAME_OVERRIDE = "override";
    public static final String DS_NAME_TSTATE = "tstate";

    public ThermostatSampleRrdDb(RrdDb rrdDb) {
        this.rrdDb = rrdDb;
    }

    public void insertSample(RrdSample rrdSample) {
        try {
            Sample sample = rrdDb.createSample();
            sample.setValue(DS_NAME_CURRENT_TEMP, rrdSample.getMetric(DS_NAME_CURRENT_TEMP));
            sample.setValue(DS_NAME_TARGET_TEMP, rrdSample.getMetric(DS_NAME_TARGET_TEMP));
            sample.setValue(DS_NAME_TMODE, rrdSample.getMetric(DS_NAME_TMODE));
            sample.setValue(DS_NAME_OVERRIDE, rrdSample.getMetric(DS_NAME_OVERRIDE));
            sample.setValue(DS_NAME_TSTATE, rrdSample.getMetric(DS_NAME_TSTATE));

            sample.setTime(TimeUnit.MILLISECONDS.toSeconds(rrdSample.getTimestamp()));
            sample.update();

            rrdDb.close();
        } catch(IOException e) {
            throw new IllegalStateException("Error constructing and adding thermostat sample to RRD " + rrdDb.getPath(), e);
        }
    }

}
