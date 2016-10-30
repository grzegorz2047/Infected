package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.grzegorz2047.databaseapi.SQLUser;

/**
 * Created by grzeg on 30.10.2016.
 */
public class PlayerChatListener implements Listener {

    private final Infected plugin;

    public PlayerChatListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        SQLUser user = plugin.getArena().getPlayer(p.getName());
        String format = plugin.getArena().getDatabaseController().getSettings().get("chat." + user.getRank().toLowerCase());
        String message = e.getMessage();
        message = message.replace('%', ' ');
        if (!user.getRank().equals("Gracz")) {
            e.setMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        e.setFormat(format.replace("{DISPLAYNAME}", p.getDisplayName()).replace("{MESSAGE}", message).replace("{LANG}", user.getLanguage()));
    }

}
