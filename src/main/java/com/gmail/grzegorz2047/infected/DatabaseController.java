package com.gmail.grzegorz2047.infected;

import com.gmail.grzegorz2047.infected.api.file.YmlFileHandler;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.databaseapi.DatabaseAPI;
import pl.grzegorz2047.databaseapi.MoneyAPI;
import pl.grzegorz2047.databaseapi.StatsAPI;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;

import java.util.HashMap;

/**
 * Created by grzeg on 21.09.2016.
 */
public class DatabaseController {

    private DatabaseAPI playerdb;
    private MessageAPI messagedb;
    private MoneyAPI moneydb;
    private HashMap<String, String> settings = new HashMap<String, String>();
    private YmlFileHandler configFile;
    private StatsAPI statsdb;

    public DatabaseController(Plugin plugin) {
        configFile = new YmlFileHandler(plugin, plugin.getDataFolder().getPath(), "config.yml");
        configFile.load();
        playerdb = new DatabaseAPI(
                configFile.getConfig().getString("mysql.player.host"),
                configFile.getConfig().getInt("mysql.player.port"),
                configFile.getConfig().getString("mysql.player.db"),
                configFile.getConfig().getString("mysql.player.user"),
                configFile.getConfig().getString("mysql.player.password")
        );
        moneydb = new MoneyAPI(
                configFile.getConfig().getString("mysql.money.host"),
                configFile.getConfig().getInt("mysql.money.port"),
                configFile.getConfig().getString("mysql.money.db"),
                configFile.getConfig().getString("mysql.money.user"),
                configFile.getConfig().getString("mysql.money.password")
        );
        moneydb.setMoneyTable(configFile.getConfig().getString("mysql.money.table"));
        statsdb = new StatsAPI(
                configFile.getConfig().getString("mysql.stats.host"),
                configFile.getConfig().getInt("mysql.stats.port"),
                configFile.getConfig().getString("mysql.stats.db"),
                configFile.getConfig().getString("mysql.stats.user"),
                configFile.getConfig().getString("mysql.stats.password"),
                configFile.getConfig().getString("mysql.stats.table")
        );
        this.settings = playerdb.getSettings();

        messagedb = new MessageAPI(
                configFile.getConfig().getString("mysql.message.host"),
                configFile.getConfig().getInt("mysql.message.port"),
                configFile.getConfig().getString("mysql.message.db"),
                configFile.getConfig().getString("mysql.message.user"),
                configFile.getConfig().getString("mysql.message.password"),
                configFile.getConfig().getString("mysql.message.minigame")
        );

    }

    public HashMap<String, String> getSettings() {
        return settings;
    }

    public MessageAPI getMessagedb() {
        return messagedb;
    }

    public DatabaseAPI getPlayerdb() {
        return playerdb;
    }

    public MoneyAPI getMoneydb() {
        return moneydb;
    }

    public StatsAPI getStatsdb() {
        return statsdb;
    }
}

