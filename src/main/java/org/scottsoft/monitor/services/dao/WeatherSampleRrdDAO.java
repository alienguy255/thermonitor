package org.scottsoft.monitor.services.dao;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.scottsoft.monitor.domain.sample.IWeatherSample;
import org.scottsoft.monitor.domain.sample.RrdSample;
import org.scottsoft.monitor.domain.sample.WeatherSample;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier(WeatherSampleRrdDAO.BEAN_ID)
public class WeatherSampleRrdDAO extends SampleDAO<WeatherSampleRrdDb, IWeatherSample>  {

    public static final String BEAN_ID = "WeatherSampleRrdDAO";

    @Override
    protected String getRrdDbFilePath(String locationId) {
        return RRD_DB_FILE_LOCATION + "/weather/" + locationId + "/" + locationId + ".rrd";
    }

    @Override
    protected long getFetchResolution() {
        return 3600L;
    }

    @Override
    protected IWeatherSample convertSample(String locationId, RrdSample rrdSample) {
        return new WeatherSample(locationId, rrdSample.getMetric(ThermostatSampleRrdDb.DS_NAME_CURRENT_TEMP), new Date(rrdSample.getTimestamp()));
    }

    @Override
    protected void ensureDirsExist(String locationId) {
        File rrdDir = new File(RRD_DB_FILE_LOCATION);
        if(!rrdDir.exists()) {
            rrdDir.mkdirs();
        }

        File weatherDir = new File(rrdDir.getAbsolutePath(), "weather");
        if(!weatherDir.exists()) {
            weatherDir.mkdirs();
        }

        File locationIdDir = new File(weatherDir.getAbsolutePath(), locationId);
        if(!locationIdDir.exists()) {
            locationIdDir.mkdirs();
        }
    }

    @Override
    protected WeatherSampleRrdDb getRrd(String filePath) {
        try {
            return new WeatherSampleRrdDb(new RrdDb(filePath));
        } catch(IOException e) {
            throw new IllegalStateException(MessageFormat.format("Critical error accessing RRD in file ''{0}''.", filePath), e);
        }
    }

    @Override
    protected WeatherSampleRrdDb createRrd(String filePath) {
        RrdDef rrdDef = new RrdDef(filePath, 3600L);  // 1 hour step
        try {
            long ARBITRARILY_OLD_DATE = 1262304000L;

            rrdDef.setVersion(2);
            rrdDef.setStartTime(ARBITRARILY_OLD_DATE);

            rrdDef.addDatasource(WeatherSampleRrdDb.DS_NAME_CURRENT_TEMP, DsType.GAUGE, 7200L, 0.0, Double.MAX_VALUE);

            rrdDef.addArchive(ConsolFun.AVERAGE, 0.5D, 1, 8760); // 8760 hours in a year
            return new WeatherSampleRrdDb(new RrdDb(rrdDef));
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("Critical error creating new RRD from definition [{0}].", rrdDef.dump()), e);
        }
    }
	
}
