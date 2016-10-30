package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.serversmanagement.ArenaStatus;

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
        e.setQuitMessage("");
        GameUser gameUser = plugin.getArena().getPlayer(p.getName());
        if (gameUser.isZombie()) {
            if (plugin.getArena().count(GameUser.PlayerStatus.ZOMBIE) <= 0) {
                if (plugin.getArena().count(GameUser.PlayerStatus.ALIVE) >= 2) {
                    plugin.getArena().removePlayer(p.getName());
                    plugin.getArena().assignFirstZombie();
                }
            }
        }
        //jeżeli zombie i rozgrywka i tylko 1 zombie wyznacz nowego zombie
        //jezeli jedna osoba restart areny
        ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());
    }
}
