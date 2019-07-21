package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.sample.RrdSample;

import java.util.Date;
import java.util.List;

public interface ISampleDAO<S> {

    List<S> getSamples(String id, Date fromTime, Date toTime);

    void insertSample(String id, RrdSample rrdSample);

}
