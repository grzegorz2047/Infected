package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Arena;
import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import com.gmail.grzegorz2047.infected.counter.CountingEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by grzeg on 30.10.2016.
 */
public class CountingTimeListener implements Listener {

    private final Infected plugin;

    public CountingTimeListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCounting(CountingEvent e) {
        int time = plugin.getCounter().getTime();
        if (plugin.getArena().isStarting()) {
            String countingPlMsg = plugin.getArena().getDatabaseController().getMessagedb().getMessage("PL", "infected.startingmsg");
            countingPlMsg = countingPlMsg.replace("%time%", String.valueOf(time));
            if (time < 5 || time % 5 == 0) {
                Bukkit.broadcastMessage(countingPlMsg);
            }
        }
        if(plugin.getArena().isInGame()){
            plugin.getArena().checkIfZombieWin();
        }
    }

}
