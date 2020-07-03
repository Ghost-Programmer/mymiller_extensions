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

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
