package org.scottsoft.monitor.common;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.config.DataCollectorAsyncConfig;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

@Slf4j
public abstract class MonitorAsyncTaskRunner<T, R> {

    @Async(DataCollectorAsyncConfig.COLLECTION_EXECUTOR_BEAN_ID)
    public void run(T object) {
        // collect the sample from the target source
        R response = collectSample(object);

        // log the time the sample was received
        long collectionTimeMs = new Date().getTime();

        // insert the sample into the db
        insertSample(object, response, collectionTimeMs);

        // notify all listening clients
        notifyClients(object, response, collectionTimeMs);
    }

    protected abstract R collectSample(T object);

    protected abstract void insertSample(T object, R response, long collectionTimeMs);

    protected abstract void notifyClients(T object, R response, long collectionTimeMs);

}
