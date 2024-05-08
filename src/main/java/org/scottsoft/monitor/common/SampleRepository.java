package org.scottsoft.monitor.common;

import com.google.common.collect.Lists;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.RrdDb;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class SampleRepository<T extends ISampleRrdDb & Closeable, S> implements ISampleRepository<S> {

    protected final String RRD_DB_FILE_LOCATION = System.getProperty("user.dir") + "/scottsoft/db/rrd";

    @Override
    public List<S> getSamples(UUID id, long fromTimeMs, long toTimeMs) {
        try (RrdDb rrdDb = RrdDb.getBuilder().setPath(getRrdDbFilePath(id)).setReadOnly(true).build()) {
            FetchData fetchData = rrdDb
                    .createFetchRequest(ConsolFun.AVERAGE, TimeUnit.MILLISECONDS.toSeconds(fromTimeMs), TimeUnit.MILLISECONDS.toSeconds(toTimeMs), getFetchResolutionSecs())
                    .fetchData();

            return IntStream.range(0, fetchData.getTimestamps().length)
                    .mapToObj(i -> new RrdSample(TimeUnit.SECONDS.toMillis(fetchData.getTimestamps()[i]),
                            Arrays.stream(fetchData.getDsNames()).collect(Collectors.toMap(Function.identity(), (dsName) -> fetchData.getValues(dsName)[i]))))
                    .map(rrdSample -> convertSample(id, rrdSample))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            return Lists.newArrayList();
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("Error occurred retrieving data from rrd file {0}", getRrdDbFilePath(id)), e);
        }
    }

    @Override
    public void insertSample(UUID id, RrdSample rrdSample) {

        ensureDirsExist(id.toString());

        File rrdDbFile = new File(getRrdDbFilePath(id));

        // TODO: can this be simplified, can the try just get an rrddb and use the same code here:
        // Sample sample = rrdDb.createSample();
        // for (String dsName : rrdDb.getDsNames()) {
        //      sample.setValue(dsName, Objects.requireNonNullElse(rrdSample.getMetric(dsName), Double.NaN));
        // }
        //
        // sample.setTime(TimeUnit.MILLISECONDS.toSeconds(rrdSample.getTimestamp()));
        // sample.update();

        try (T rrdDb = rrdDbFile.exists() ? getRrd(rrdDbFile.getAbsolutePath()) : createRrd(rrdDbFile.getAbsolutePath())) {
            rrdDb.insertSample(rrdSample);
        } catch (IOException e) {
            throw new IllegalStateException("An error occurred adding sample to RRD " + rrdDbFile.getAbsolutePath(), e);
        }
    }

    protected abstract S convertSample(UUID id, RrdSample rrdSample);

    protected abstract void ensureDirsExist(String id);

    protected abstract T getRrd(String filePath);

    protected abstract T createRrd(String filePath);

    protected abstract String getRrdDbFilePath(UUID id);

    protected abstract long getFetchResolutionSecs();

}
