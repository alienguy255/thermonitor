package org.scottsoft.monitor.thermostat;

public interface TStatCollectionSample {

    Double temp();

    Double tmode();

    Double override();

    Double targetTemp();

    Double tstate();

}
