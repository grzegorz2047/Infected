package com.gmail.grzegorz2047.infected;

import com.gmail.grzegorz2047.infected.counter.Counter;
import com.gmail.grzegorz2047.infected.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by grzegorz2047 on 29.10.2016.
 */
public class Infected extends JavaPlugin {

    private Counter counter;
    private Arena arena;

    @Override
    public void onEnable() {
        counter = new Counter();
        Bukkit.getScheduler().runTaskTimer(this, counter, 0, 20l);
        registerListeners();
        arena = new Arena(this);
        arena.init();
        CommandController commands = new CommandController(this);
        System.out.println(this.getName() + " zostal wylaczony!");
    }

    @Override
    public void onDisable() {
        System.out.println(this.getName() + " zostal wlaczony!");
    }


    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlaceBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CounterEndTimeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CountingTimeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockVariousInteractionListener(this), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    public Counter getCounter() {
        return this.counter;
    }

    public Arena getArena() {
        return arena;
    }
}
