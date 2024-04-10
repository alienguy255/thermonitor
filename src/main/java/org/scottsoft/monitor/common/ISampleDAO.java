package org.scottsoft.monitor.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ISampleDAO<S> {

    List<S> getSamples(UUID id, Date fromTime, Date toTime);

    void insertSample(UUID id, RrdSample rrdSample);

}
