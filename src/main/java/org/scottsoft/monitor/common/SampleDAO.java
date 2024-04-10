package org.scottsoft.monitor.common;

import com.google.common.collect.Lists;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.RrdDb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class SampleDAO<T extends ISampleRrdDb, S> implements ISampleDAO<S> {

    protected final String RRD_DB_FILE_LOCATION = System.getProperty("user.dir") + "/scottsoft/db/rrd";

    @Override
    public List<S> getSamples(UUID id, Date fromTime, Date toTime) {

        //List<S> samples = Lists.newArrayList();
        RrdDb rrdDb = null;

        try {
            rrdDb = new RrdDb(getRrdDbFilePath(id), true);
            FetchData fetchData = rrdDb
                    .createFetchRequest(ConsolFun.AVERAGE, TimeUnit.MILLISECONDS.toSeconds(fromTime.getTime()), TimeUnit.MILLISECONDS.toSeconds(toTime.getTime()), getFetchResolution())
                    .fetchData();

            return IntStream.range(0, fetchData.getTimestamps().length)
                    .mapToObj(i -> new RrdSample(TimeUnit.SECONDS.toMillis(fetchData.getTimestamps()[i]),
                            Arrays.stream(fetchData.getDsNames()).collect(Collectors.toMap(Function.identity(), (dsName) -> fetchData.getValues(dsName)[i])))
                    )
                    .map(rrdSample -> convertSample(id, rrdSample))
                    .collect(Collectors.toList());

            //for(RrdSample rrdSample : rrdSamples) {
            //    samples.add(convertSample(id, rrdSample));
            //}
        } catch (FileNotFoundException e) {
            return Lists.newArrayList();
        } catch(IOException e) {
            throw new IllegalStateException(MessageFormat.format("Error occurred retrieving data from rrd file {0}", getRrdDbFilePath(id)), e);
        } finally {
            closeRrdDb(rrdDb);
        }
        //return samples;
    }

    @Override
    public void insertSample(UUID id, RrdSample rrdSample) {

        ensureDirsExist(id.toString());

        File rrdDbFile = new File(getRrdDbFilePath(id));
        T rrdDb = rrdDbFile.exists() ? getRrd(rrdDbFile.getAbsolutePath()) : createRrd(rrdDbFile.getAbsolutePath());

        rrdDb.insertSample(rrdSample);
    }

    protected abstract S convertSample(UUID id, RrdSample rrdSample);

    protected abstract void ensureDirsExist(String id);

    protected abstract T getRrd(String filePath);

    protected abstract T createRrd(String filePath);

    protected abstract String getRrdDbFilePath(UUID id);

    protected abstract long getFetchResolution();

    public static void closeRrdDb(RrdDb rrdDb) {
        try {
            if (rrdDb != null) {
                rrdDb.close();
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Critical failure: IO Error closing RRD database in file: " + rrdDb.getPath(), ioe);
        }
    }

}
