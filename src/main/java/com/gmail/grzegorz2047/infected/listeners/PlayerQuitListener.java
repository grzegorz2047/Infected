package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Arena;
import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import com.gmail.grzegorz2047.infected.api.util.ColoringUtil;
import com.gmail.grzegorz2047.infected.scoreboard.ScoreboardAPI;
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
        if (plugin.getArena().isStarting()) {
            gameUser.changePlayerStatus(GameUser.PlayerStatus.SPECTATOR);
            System.out.print("Wychodzi " + p.getName() + " podczas startu areny");
            if (!plugin.getArena().isEnoughToStart()) {
                System.out.print("Wychodzi " + p.getName() + " podczas startu areny kiedy nie ma enough players");
                plugin.getCounter().cancel();
                plugin.getArena().status = Arena.Status.WAITING;

                Bukkit.broadcastMessage(ColoringUtil.colorText("&cNie wystarczajaca liczba graczy"));
            }
        } else if (plugin.getArena().isInGame()) {
            System.out.print("Wychodzacy gracz " + gameUser.getPlayerStatus().toString());
            if (gameUser.isZombie()) {
                if (plugin.getArena().count(GameUser.PlayerStatus.ZOMBIE) <= 1) {
                    System.out.print("tylko 1 zombie na arenie");

                    if (plugin.getArena().count(GameUser.PlayerStatus.ALIVE) >= 2) {
                        System.out.print("2 lub wiecej zyjacych");

                        gameUser.changePlayerStatus(GameUser.PlayerStatus.SPECTATOR);
                        GameUser choosen = plugin.getArena().getRandomGameUser();
                        Player choosenPlayer = Bukkit.getPlayer(choosen.getUsername());
                        plugin.getArena().makePlayerZombie(choosenPlayer, choosen);
                    } else {
                        System.out.print("Mniej niz 2 zyjace osoby");
                        Bukkit.broadcastMessage(ColoringUtil.colorText("&6Zbyt malo graczy do przeprowadzenia areny!"));
                        plugin.getArena().reInit();
                    }
                }
            }
        }
        ScoreboardAPI scoreboardAPI = new ScoreboardAPI(plugin);


        plugin.getArena().removePlayer(p.getName());
        //jeżeli zombie i rozgrywka i tylko 1 zombie wyznacz nowego zombie
        //jezeli jedna osoba restart areny
        ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());
        scoreboardAPI.refreshTags();
    }
}
