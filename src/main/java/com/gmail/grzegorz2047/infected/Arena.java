package com.gmail.grzegorz2047.infected;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.StatsUser;
import pl.grzegorz2047.serversmanagement.ArenaStatus;

import java.util.*;

/**
 * Created by grzeg on 29.10.2016.
 */
public class Arena {
    private final Infected plugin;
    private HashMap<String, GameUser> playersData = new HashMap<String, GameUser>();
    private DatabaseController databaseController;
    private int minPlayers = 3;
    private int startTime = 40;
    private int ingameTime = 60 * 7;
    private Location zombieIngameSpawn;
    private Location aliveIngameSpawn;

    public enum Status {WAITING, STARTING, SEARCHING, INGAME}

    public Status status = Status.WAITING;

    public Arena(Plugin plugin) {
        this.plugin = (Infected) plugin;
        databaseController = new DatabaseController(plugin);
    }

    public void addPlayer(Player p, GameUser.PlayerStatus playerStatus) {
        databaseController.getMoneydb().insertPlayer(p.getName());
        databaseController.getStatsdb().insertPlayer(p.getName());
        int money = databaseController.getMoneydb().getPlayer(p.getName());
        //plugin.getPlayerManager().changePlayerExp(p.getName(), 100);
        SQLUser user = databaseController.getPlayerdb().getPlayer(p);
        StatsUser statsUser = databaseController.getStatsdb().getPlayer(p);
        GameUser gameUser = new GameUser(user, statsUser, money, playerStatus);
        playersData.put(p.getName(), gameUser);

    }

    public void removePlayer(String name) {
        playersData.remove(name);
    }

    public GameUser getPlayer(String p) {
        return playersData.get(p);
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public void init() {
        ArenaStatus.initStatus(Bukkit.getMaxPlayers());
        ArenaStatus.setStatus(ArenaStatus.Status.WAITING);
        ArenaStatus.setLore("§7§l> §a1.7 - 1.10");
    }

    public void reInit() {
        ArenaStatus.initStatus(Bukkit.getMaxPlayers());
        ArenaStatus.setStatus(ArenaStatus.Status.WAITING);
        ArenaStatus.setLore("§7§l> §a1.7 - 1.10");
    }

    public void preStartArena() {
        plugin.getCounter().start(startTime);
    }

    public void startArena() {
        plugin.getArena().assignZombie();
        plugin.getCounter().start(ingameTime);
    }

    public HashMap<String, GameUser> getPlayersData() {
        return playersData;
    }

    public boolean isWaiting() {
        return status.equals(Status.WAITING);
    }

    public boolean isInGame() {
        return status.equals(Status.INGAME);
    }

    public boolean isStarting() {
        return status.equals(Status.STARTING);
    }

    public boolean isSearchingZombie() {
        return status.equals(Status.SEARCHING);
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public boolean isEnoughToStart() {
        return getMinPlayers() >= getPlayersData().size();
    }

    public int count(GameUser.PlayerStatus status) {
        int sumOfPlayers = 0;
        for (GameUser gameUser : playersData.values()) {
            if (gameUser.getPlayerStatus().equals(status)) {
                sumOfPlayers++;
            }
        }
        return sumOfPlayers;
    }

    public int countAll() {
        return playersData.size();
    }

    public void assignZombie() {
        Random r = new Random();
        String[] tab = new String[1];

        List<GameUser> list = new ArrayList<>(this.getPlayersData().values());
        int chose = r.nextInt(list.size());
        GameUser chosen = list.get(chose);
        tab[0] = chosen.getUsername();
        for (GameUser gameUser : list) {
            String message = getDatabaseController().getMessagedb().getMessage(gameUser.getLanguage(), "infected.startmsg");
            Player p = Bukkit.getPlayer(gameUser.getUsername());
            p.sendMessage(message);
            if (gameUser.equals(chosen)) {
                DisguiseAPI.disguiseIgnorePlayers(p, new MobDisguise(DisguiseType.ZOMBIE), tab);
                String choosenmsg = getDatabaseController().getMessagedb().getMessage(gameUser.getLanguage(), "infected.playerchoosen");
                p.sendMessage(choosenmsg);
                p.teleport(zombieIngameSpawn);
            } else {
                p.teleport(aliveIngameSpawn);
            }
            p.playSound(p.getLocation(), Sound.GHAST_SCREAM, r.nextInt(6), 1);
        }
        //Bukkit.broadcastMessage("Gracz " + chosen.getUsername() + " zostal zaatakowany przez tajemnicza istote i zakażony przez co stał się zombie ")

    }

}
