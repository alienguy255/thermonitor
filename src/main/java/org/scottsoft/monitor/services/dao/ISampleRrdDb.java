package org.scottsoft.monitor.services.dao;

import org.scottsoft.monitor.domain.sample.RrdSample;

public interface ISampleRrdDb {

    void insertSample(RrdSample rrdSample);

}
