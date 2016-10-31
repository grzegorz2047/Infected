package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import com.gmail.grzegorz2047.infected.counter.Counter;
import com.gmail.grzegorz2047.infected.scoreboard.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
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
        GameUser gameUser = null;
        if (plugin.getArena().isWaiting()) {
            gameUser = plugin.getArena().addPlayer(p, GameUser.PlayerStatus.ALIVE);
        } else if (plugin.getArena().isStarting()) {
            gameUser = plugin.getArena().addPlayer(p, GameUser.PlayerStatus.ALIVE);
        } else if (plugin.getArena().isInGame()) {
            gameUser = plugin.getArena().addPlayer(p, GameUser.PlayerStatus.SPECTATOR);
            p.setGameMode(GameMode.SPECTATOR);
        }
        ScoreboardAPI scoreboardAPI = new ScoreboardAPI(plugin);
        scoreboardAPI.createScoreboard(p, gameUser);
        preparePlayer(p);
        try {
            p.teleport(plugin.getArena().getSpawn());
        } catch (NullPointerException ex) {
            System.out.print("Brak ustalonego glownego spawnu");
        }
        System.out.print(plugin.getArena().getStatus().toString() + " Status areny");
        System.out.print(plugin.getCounter().getStatus().toString() + " " + plugin.getCounter().getTime());
        ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());
        if (plugin.getArena().isWaiting()) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                scoreboardAPI.updateDisplayName(0, players);
            }
            if (plugin.getArena().isEnoughToStart()) {
                plugin.getArena().preStartArena();
            }
        }
        scoreboardAPI = new ScoreboardAPI(plugin);
        scoreboardAPI.refreshTags();
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();


    }

    private void preparePlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (Player pls : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(pls);
            }
        }
    }

}
