package com.gmail.grzegorz2047.infected;

import com.gmail.grzegorz2047.infected.counter.Counter;
import com.gmail.grzegorz2047.infected.listeners.DeathListener;
import com.gmail.grzegorz2047.infected.listeners.EntityDamageByEntityListener;
import com.gmail.grzegorz2047.infected.listeners.JoinListener;
import com.gmail.grzegorz2047.infected.listeners.LoginListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by grzegorz2047 on 29.10.2016.
 */
public class Infected extends JavaPlugin {

    private Counter counter;

    @Override
    public void onEnable() {
        counter = new Counter(this);
        Bukkit.getScheduler().runTaskTimer(this, counter, 0, 20l);
        registerListeners();
        System.out.println(this.getName() + " zostal wylaczony!");
    }

    @Override
    public void onDisable() {
        System.out.println(this.getName() + " zostal wlaczony!");
    }


    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
    }
    public Counter getCounter(){
        return this.counter;
    }
}
