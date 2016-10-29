package com.gmail.grzegorz2047.infected.counter;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by grzegorz2047 on 17.05.2016.
 */
public class CounterEndEvent extends Event {
    private final Counter.CounterStatus status;
    private final Counter counter;
    private static final HandlerList handlers = new HandlerList();

    public CounterEndEvent(Counter.CounterStatus status, Counter counter) {
        this.status = status;
        this.counter = counter;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Counter.CounterStatus getStatus() {
        return status;
    }

    public Counter getCounter() {
        return counter;
    }
}
