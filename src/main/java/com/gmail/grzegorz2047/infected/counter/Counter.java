package com.gmail.grzegorz2047.infected.counter;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Bukkit;

/**
 * Created by grzegorz2047 on 16.05.2016.
 */
public class Counter implements Runnable {

    public int getTime() {
        return time;
    }

    private int time;
    private boolean running = false;

    public Counter() {

    }

    private CounterStatus status = CounterStatus.STOPPED;

    @Override
    public void run() {
        count();
    }

    public enum CounterStatus {
        STOPPED,
        RUNNING,
        PAUSED
    }

    public void setPaused(boolean paused) {
        this.running = paused;
    }

    public CounterStatus getStatus() {
        return status;
    }

    public void setStatus(CounterStatus status) {
        this.status = status;
        System.out.println("STATUS " + status.toString());
    }

    public void cancel() {
        System.out.println("CANCEL");
        this.setStatus(CounterStatus.STOPPED);
        this.running = false;
    }

    public void start(int seconds) {
        if (running) {
            System.out.println("ERROR?! Counter juz odlicza! Resetuje!");
        }
        this.time = seconds;
        this.running = true;
        this.setStatus(CounterStatus.RUNNING);
    }

    public boolean isRunning() {
        return running;
    }

    private void count() {
        if (this.running) {
            if (time > 0) {
                time--;
                Bukkit.getPluginManager().callEvent(new CountingEvent(this.getStatus(), this));
            } else {
                Bukkit.getPluginManager().callEvent(new CounterEndEvent(this.getStatus(), this));
                cancel();
            }
        }
    }
}
