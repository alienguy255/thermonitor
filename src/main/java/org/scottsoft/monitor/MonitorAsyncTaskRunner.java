package org.scottsoft.monitor;

import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.config.AsyncConfig;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public abstract class MonitorAsyncTaskRunner<T, R> {

    @Async(AsyncConfig.COLLECTION_EXECUTOR_BEAN_ID)
    public void run(T object) {
        // collect the sample from the target source
        R response = collectSample(object);

        // insert the sample into the db
        insertSample(object, response);

        // notify all listening clients
        notifyClients(object, response);
    }

    protected abstract R collectSample(T object);

    protected abstract void insertSample(T object, R response);

    protected abstract void notifyClients(T object, R response);

}
