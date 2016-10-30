package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * Created by grzeg on 30.10.2016.
 */
public class PlayerQuitListener implements Listener {

    private final Infected plugin;

    public PlayerQuitListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.getArena().removePlayer(p.getName());
        //jeżeli zombie i rozgrywka i tylko 1 zombie wyznacz nowego zombie
        //jezeli jedna osoba restart areny
    }
}
