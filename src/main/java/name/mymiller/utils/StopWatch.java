package name.mymiller.utils;

import java.time.Duration;
import java.time.Instant;

public class StopWatch {
    private Instant start = null;
    private Instant finish = null;

    protected StopWatch() {
    }

    public StopWatch(Instant start) {
        this.start = start;
    }

    public static StopWatch now() {
        return new StopWatch().setStart(Instant.now());
    }

    public StopWatch finish() {
        return this.setFinish(Instant.now());
    }

    public Instant getStart() {
        return start;
    }

    public Instant getFinish() {
        return finish;
    }

    protected StopWatch setStart(Instant start) {
        this.start = start;
        return this;
    }

    protected StopWatch setFinish(Instant finish) {
        this.finish = finish;
        return this;
    }

    @Override
    public String toString() {
        if (this.start == null) {
            return "Not Started";
        } else if (this.finish != null) {
            return formatDuration(Duration.between(start, finish));
        }
        return formatDuration(Duration.between(start, Instant.now()));
    }

    public Long getMillieseconds() {
        if(this.finish == null) {
            return Instant.now().toEpochMilli() - this.start.toEpochMilli();
        }

        return this.finish.toEpochMilli() - this.start.toEpochMilli();
    }

    private String formatDuration(Duration duration) {
        return  String.format(
                "%d(d):%02d(h):%02d(m):%02d(s):%03d(ms):%06d(ns)",
                duration.toDaysPart(),
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart(),
                duration.toMillisPart(),
                duration.getNano() % 1000000);
    }
}
