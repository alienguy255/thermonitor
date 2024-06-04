package org.scottsoft.monitor.weather;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.scottsoft.monitor.common.SampleRepository;
import org.scottsoft.monitor.common.RrdSample;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WeatherSampleRepository extends SampleRepository<IWeatherSample> {

    public static final String DS_NAME_CURRENT_TEMP = "currentTemp";

    @Override
    protected String getRrdDbFilePath(UUID locationId) {
        return RRD_DB_FILE_LOCATION + "/weather/" + locationId + "/" + locationId + ".rrd";
    }

    @Override
    protected long getFetchResolutionSecs() {
        return 3600L;
    }

    @Override
    protected IWeatherSample convertSample(UUID locationId, RrdSample rrdSample) {
        return new WeatherSample(locationId, rrdSample.getMetric(DS_NAME_CURRENT_TEMP), new Date(rrdSample.getTimestamp()));
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
    protected RrdDb createRrd(String filePath) {
        RrdDef rrdDef = new RrdDef(filePath, 3600L);  // 1 hour step
        try {
            long ARBITRARILY_OLD_DATE = 1262304000L;

            rrdDef.setVersion(2);
            rrdDef.setStartTime(ARBITRARILY_OLD_DATE);

            rrdDef.addDatasource(DS_NAME_CURRENT_TEMP, DsType.GAUGE, 7200L, 0.0, Double.MAX_VALUE);

            // 8760 hours in a year
            // 8760 * 5 = 43800 hours in 5 years
            rrdDef.addArchive(ConsolFun.AVERAGE, 0.5D, 1, 43800);

            return RrdDb.of(rrdDef);
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("Critical error creating new RRD from definition [{0}].", rrdDef.dump()), e);
        }
    }
	
}
