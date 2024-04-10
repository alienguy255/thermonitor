package org.scottsoft.monitor.thermostat;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDef;
import org.scottsoft.monitor.common.SampleDAO;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import org.rrd4j.core.RrdDb;

@Component
@Qualifier(ThermostatSampleRrdDAO.BEAN_ID)
public class ThermostatSampleRrdDAO extends SampleDAO<ThermostatSampleRrdDb, IThermostatSample> {

    public static final String BEAN_ID = "ThermostatSampleRrdDAO";

    @Override
    protected String getRrdDbFilePath(UUID thermostatId) {
        return RRD_DB_FILE_LOCATION + "/thermostat/" + thermostatId + "/" + thermostatId + ".rrd";
    }

    @Override
    protected long getFetchResolution() {
        return 60L;
    }

    @Override
    protected IThermostatSample convertSample(UUID thermostatId, RrdSample rrdSample) {
        return new ThermostatSample(thermostatId, rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_CURRENT_TEMP), rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_TMODE),
                rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_OVERRIDE), rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_TARGET_TEMP), rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_TSTATE),
                new Date(rrdSample.getTimestamp()));
    }

    @Override
    protected void ensureDirsExist(String thermostatId) {
        File rrdDir = new File(RRD_DB_FILE_LOCATION);
        if(!rrdDir.exists()) {
            rrdDir.mkdirs();
        }

        File thermostatDir = new File(rrdDir.getAbsolutePath(), "thermostat");
        if(!thermostatDir.exists()) {
            thermostatDir.mkdirs();
        }

        File thermostatIdDir = new File(thermostatDir.getAbsolutePath(), thermostatId);
        if(!thermostatIdDir.exists()) {
            thermostatIdDir.mkdirs();
        }
    }

    @Override
    protected ThermostatSampleRrdDb getRrd(String filePath) {
        try {
            return new ThermostatSampleRrdDb(new RrdDb(filePath));
        } catch(IOException e) {
            throw new IllegalStateException(MessageFormat.format("Critical error accessing RRD in file ''{0}''.", filePath), e);
        }
    }

    @Override
    protected ThermostatSampleRrdDb createRrd(String filePath) {
        RrdDef rrdDef = new RrdDef(filePath, 60L);
        try {
            long ARBITRARILY_OLD_DATE = 1262304000L;

            rrdDef.setVersion(2);
            rrdDef.setStartTime(ARBITRARILY_OLD_DATE);

            rrdDef.addDatasource(ThermostatSampleRrdDb.DS_NAME_CURRENT_TEMP, DsType.GAUGE, 120L, 0.0, Double.MAX_VALUE);
            rrdDef.addDatasource(ThermostatSampleRrdDb.DS_NAME_TARGET_TEMP, DsType.GAUGE, 120L, 0.0, Double.MAX_VALUE);
            rrdDef.addDatasource(ThermostatSampleRrdDb.DS_NAME_TMODE, DsType.GAUGE, 120L, 0.0D, 1.0D);
            rrdDef.addDatasource(ThermostatSampleRrdDb.DS_NAME_OVERRIDE, DsType.GAUGE, 120L, 0.0D, 1.0D);
            rrdDef.addDatasource(ThermostatSampleRrdDb.DS_NAME_TSTATE, DsType.GAUGE, 120L, 0.0D, 1.0D);

            rrdDef.addArchive(ConsolFun.AVERAGE, 0.5D, 1, 525600); // TODO: 525600 mins in a year, configure this for much more!.... do the same for weather samples
            return new ThermostatSampleRrdDb(new RrdDb(rrdDef));
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("Critical error creating new RRD from definition [{0}].", rrdDef.dump()), e);
        }
    }
	
}
