package com.gmail.grzegorz2047.infected;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by grzeg on 29.10.2016.
 */
public class Infected extends JavaPlugin {

    @Override
    public void onDisable() {
        System.out.println(this.getName()+ " zostal wlaczony!");
    }

    @Override
    public void onEnable() {
        System.out.println(this.getName()+ " zostal wylaczony!");
    }
}
