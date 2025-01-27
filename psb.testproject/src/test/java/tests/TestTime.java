package tests;

import org.junit.Assert;
import org.junit.Test;
import psb.testproject.timer.ITimer;
import psb.testproject.timer.Timer;

import java.util.concurrent.TimeUnit;

public class TestTime {
    @Test
    public void testtime() {
        Timer timer = new Timer();
        timer.start();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("sec: " + (i + 1));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();
        timer.stop(new ITimer() {
            @Override
            public void time(long time) {
                Assert.assertEquals(TimeUnit.MILLISECONDS.toSeconds(time), 5);
            }
        });
    }
}
