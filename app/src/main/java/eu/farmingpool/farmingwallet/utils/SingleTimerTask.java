package eu.farmingpool.farmingwallet.utils;

import java.util.Timer;
import java.util.TimerTask;

public class SingleTimerTask {
    private final Runnable runnable;
    private Timer timer;
    private TimerTask timerTask;
    private long delay;
    private long period;

    public SingleTimerTask(Runnable runnable, long delay, long period) {
        this.runnable = runnable;
        this.delay = delay;
        this.period = period;
    }

    public void start() {
        if (isRunning()) {
            return;
        }

        createTimerTask();

        timer = new Timer();

        if (period == 0) {
            timer.schedule(timerTask, delay);
        } else {
            timer.scheduleAtFixedRate(timerTask, delay, period);
        }
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void restart() {
        stop();
        start();
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    public boolean isRunning() {
        return timer != null;
    }

    private void createTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (period == 0 && timer != null) {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }

                runnable.run();
            }
        };
    }
}