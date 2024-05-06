package org.scottsoft.monitor.common;

import java.io.IOException;

public interface ISampleRrdDb {

    void insertSample(RrdSample rrdSample) throws IOException;

}
