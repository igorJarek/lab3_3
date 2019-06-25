package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.HOURS;

public class FakeClock extends Clock {

    @Override
    public ZoneId getZone() {
        return null;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        return Instant.now().minus(25, HOURS);
    }
}
