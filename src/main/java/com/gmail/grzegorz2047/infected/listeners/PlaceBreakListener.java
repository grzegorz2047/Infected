package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * Created by grzeg on 30.10.2016.
 */
public class PlaceBreakListener implements Listener {

    private final Infected plugin;

    public PlaceBreakListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
    }

    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
    }

    public void onEmpty(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
    }

    public void onFill(PlayerBucketFillEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
    }
}
