package com.gmail.grzegorz2047.infected.counter;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Bukkit;

/**
 * Created by grzegorz2047 on 16.05.2016.
 */
public class Counter implements Runnable{
    private final Infected plugin;

    public int getTime() {
        return time;
    }

    private int time;
    private boolean running = false;

    public Counter(Infected plugin) {
        this.plugin = plugin;
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

    public CounterStatus getStatus() {
        return status;
    }

    public void setStatus(CounterStatus status) {
        this.status = status;
    }

    public void cancel() {
        this.setStatus(CounterStatus.STOPPED);
        this.running = false;
    }

    public void start(CounterStatus status) {
        this.running = true;
        this.setStatus(status);
    }

    public boolean isRunning() {
        return running;
    }

    public void count() {
        if (this.running) {
            if (time > 0) {
                time--;
                Bukkit.getPluginManager().callEvent(new CountingEvent(this.getStatus(), this));
            } else {
                Bukkit.getPluginManager().callEvent(new CounterEndEvent(this.getStatus(), this));
            }
        }
    }
}
