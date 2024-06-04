package org.scottsoft.monitor.thermostat;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDef;
import org.scottsoft.monitor.common.SampleRepository;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.stereotype.Component;

import org.rrd4j.core.RrdDb;

@Component
public class ThermostatSampleRepository extends SampleRepository<IThermostatSample> {

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";
    public static final String DS_NAME_TARGET_TEMP = "targetTemp";
    public static final String DS_NAME_TSTATE = "tstate";

    @Override
    protected String getRrdDbFilePath(UUID thermostatId) {
        return RRD_DB_FILE_LOCATION + "/thermostat/" + thermostatId + "/" + thermostatId + ".rrd";
    }

    @Override
    protected long getFetchResolutionSecs() {
        return 60L;
    }

    @Override
    protected IThermostatSample convertSample(UUID thermostatId, RrdSample rrdSample) {
        return new ThermostatSample(thermostatId, rrdSample.getMetric(DS_NAME_CURRENT_TEMP), rrdSample.getMetric(DS_NAME_TARGET_TEMP),
                rrdSample.getMetric(DS_NAME_TSTATE), new Date(rrdSample.getTimestamp()));
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
    protected RrdDb createRrd(String filePath) {
        RrdDef rrdDef = new RrdDef(filePath, 60L); // 1 min step
        try {
            long ARBITRARILY_OLD_DATE = 1262304000L;

            rrdDef.setVersion(2);
            rrdDef.setStartTime(ARBITRARILY_OLD_DATE);

            rrdDef.addDatasource(DS_NAME_CURRENT_TEMP, DsType.GAUGE, 120L, 0.0, Double.MAX_VALUE);
            rrdDef.addDatasource(DS_NAME_TARGET_TEMP, DsType.GAUGE, 120L, 0.0, Double.MAX_VALUE);
            rrdDef.addDatasource(DS_NAME_TSTATE, DsType.GAUGE, 120L, 0.0D, 1.0D);

            // 525600 mins in a year
            // 525600 * 5 = 2628000 mins in 5 years
            rrdDef.addArchive(ConsolFun.AVERAGE, 0.5D, 1, 2628000); // 2628000 mins in 5 years

            return RrdDb.of(rrdDef);
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("An error occurred creating new RRD from definition [{0}].", rrdDef.dump()), e);
        }
    }
	
}
