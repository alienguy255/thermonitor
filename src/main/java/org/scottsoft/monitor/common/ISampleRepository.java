package org.scottsoft.monitor.common;

import java.util.List;
import java.util.UUID;

public interface ISampleRepository<S> {

    List<S> getSamples(UUID id, long fromTimeMs, long toTimeMs);

    void insertSample(UUID id, RrdSample rrdSample);

}
