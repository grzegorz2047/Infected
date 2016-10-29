package com.gmail.grzegorz2047.infected;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.StatsUser;

import java.util.HashMap;

/**
 * Created by grzeg on 29.10.2016.
 */
public class Arena {
    //private HashMap<String, GameUser.PlayerType> playerIngameType = new HashMap<String, GameUser.PlayerType>();
    private HashMap<String, GameUser> playersData = new HashMap<String, GameUser>();
    private DatabaseController databaseController;

    public Arena(Plugin plugin) {
        databaseController = new DatabaseController(plugin);
    }

    public void addPlayer(Player p) {
        databaseController.getMoneydb().insertPlayer(p.getName());
        databaseController.getStatsdb().insertPlayer(p.getName());
        int money = databaseController.getMoneydb().getPlayer(p.getName());
        //plugin.getPlayerManager().changePlayerExp(p.getName(), 100);
        SQLUser user = databaseController.getPlayerdb().getPlayer(p);
        StatsUser statsUser = databaseController.getStatsdb().getPlayer(p);
        GameUser gameUser = new GameUser(user, statsUser, money);
        playersData.put(p.getName(), gameUser);

    }

    public void removePlayer(String name) {
        playersData.remove(name);
    }
    public GameUser getPlayer(String p){
        return playersData.get(p);
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}
