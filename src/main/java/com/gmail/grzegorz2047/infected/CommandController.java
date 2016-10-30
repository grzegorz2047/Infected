package com.gmail.grzegorz2047.infected;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by grzeg on 23.09.2016.
 */
public class CommandController implements CommandExecutor {
    private final Infected plugin;

    public CommandController(Infected plugin) {
        this.plugin = plugin;
        plugin.getCommand("infected").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Wykonanie komendy jedynie z poziomu gry!");
            return true;
        }
        Player p = (Player) sender;
        if (!p.isOp()) {
            p.sendMessage("nope!");
            return false;
        }
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    plugin.getArena().setSpawn(p.getLocation());
                    plugin.getArena().saveLocationsToFile();
                    p.sendMessage("General spawn set dla swiata" + p.getWorld().getName());
                }
                if (args[1].equalsIgnoreCase("alive")) {
                    plugin.getArena().setAliveIngameSpawn(p.getLocation());
                    plugin.getArena().saveLocationsToFile();
                    p.sendMessage("alive spawn set dla " + p.getWorld().getName());
                }
                if (args[1].equalsIgnoreCase("zombie")) {
                    plugin.getArena().setZombieIngameSpawn(p.getLocation());
                    plugin.getArena().saveLocationsToFile();
                    p.sendMessage("zombie spawn set dla " + p.getWorld().getName());

                }
            }
            if (args[0].equalsIgnoreCase("tp")) {
                p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                p.sendMessage("teleportowano do " + args[1]);
            }
        }
        return true;
    }
}
