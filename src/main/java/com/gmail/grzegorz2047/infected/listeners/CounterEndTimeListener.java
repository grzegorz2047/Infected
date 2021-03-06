package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import com.gmail.grzegorz2047.infected.api.util.BungeeUtil;
import com.gmail.grzegorz2047.infected.counter.CounterEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by grzeg on 30.10.2016.
 */
public class CounterEndTimeListener implements Listener {

    private final Infected plugin;

    public CounterEndTimeListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnd(CounterEndEvent e) {
        if (plugin.getArena().isStarting()) {
            plugin.getArena().startArena();
        }
        else if (plugin.getArena().isInGame()) {
            plugin.getArena().checkIfAliveWin();
        }
    }
}
