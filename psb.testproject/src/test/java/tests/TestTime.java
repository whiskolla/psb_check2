package tests;

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
            System.out.println("stop");
        });
        thread.run();
        timer.stop(new ITimer() {
            @Override
            public void time(long time) {
                System.out.println("Прошло " + TimeUnit.MILLISECONDS.toSeconds(time) + " секунд");
            }
        });
    }
}
