package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import com.gmail.grzegorz2047.infected.counter.Counter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.serversmanagement.ArenaStatus;

/**
 * Created by grzeg on 29.10.2016.
 */
public class JoinListener implements Listener {

    private final Infected plugin;

    public JoinListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        preparePlayer(p);
        ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());

        if (plugin.getArena().isWaiting()) {
            plugin.getArena().addPlayer(p, GameUser.PlayerStatus.ALIVE);
            if (plugin.getArena().isEnoughToStart()) {
                plugin.getArena().preStartArena();

            }
        }
        if (plugin.getArena().isWaiting()) {
            plugin.getArena().addPlayer(p, GameUser.PlayerStatus.SPECTATOR);
        }
    }
    private void preparePlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
    }

}
