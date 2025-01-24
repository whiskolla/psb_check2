package psb.testproject.timer;

import psb.testproject.timer.ITimer;

public class Timer {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop(ITimer t) {
        long endTime = System.currentTimeMillis();
        long timeSpent = endTime - startTime;
        t.time(timeSpent);
    }
}
