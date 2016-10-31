package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by grzeg on 31.10.2016.
 */
public class BlockVariousInteractionListener implements Listener {

    private final Infected plugin;

    public BlockVariousInteractionListener(Infected plugin) {
        this.plugin = plugin;
    }

    public void onBlockIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    /*    @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            Player attacker = e.getPlayer();
            ItemStack hand = attacker.getItemInHand();
            if (hand != null) {
                if (hand.getType().equals(Material.STICK)) {
                    long lastUsage = plugin.getArena().getDelayedKnockback().get(attacker.getName());
                    if (System.currentTimeMillis() - lastUsage > 10000) {
                        plugin.getArena().getDelayedKnockback().put(attacker.getName(), System.currentTimeMillis());
                        e.setCancelled(true);
                    }else {
                        attacker.sendMessage("Uzycie za "+ (10000 - System.currentTimeMillis() - lastUsage ));
                    }
                }
            }

        }
    }
    */
    @EventHandler
    public void onInv(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onInv(InventoryClickEvent e) {
        e.setCancelled(true);
    }

}
