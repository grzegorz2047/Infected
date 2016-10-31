package com.gmail.grzegorz2047.infected;

import com.gmail.grzegorz2047.infected.api.util.BungeeUtil;
import com.gmail.grzegorz2047.infected.kits.StartKits;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.StatsUser;
import pl.grzegorz2047.serversmanagement.ArenaStatus;
import pl.grzegorz2047.serversmanagement.api.file.YmlFileHandler;
import pl.grzegorz2047.serversmanagement.api.util.LocationUtil;

import java.io.File;
import java.util.*;

/**
 * Created by grzeg on 29.10.2016.
 */
public class Arena {
    private final Infected plugin;
    private HashMap<String, GameUser> playersData = new HashMap<String, GameUser>();
    private HashMap<String, Long> delayedKnockback = new HashMap<String, Long>();
    private DatabaseController databaseController;
    private int minPlayers = 3;
    private int maxPlayers = 20;
    private int startTime = 40;
    private int ingameTime = 60 * 7;
    private Location zombieIngameSpawn;
    private Location aliveIngameSpawn;
    private Location spawn;
    private YmlFileHandler spawnsFile;
    private boolean changingPlayerEnabled = true;

    public enum Status {WAITING, STARTING, SEARCHING, INGAME}

    public Status status = Status.WAITING;

    public Arena(Plugin plugin) {
        this.plugin = (Infected) plugin;
        init();
        databaseController = new DatabaseController(plugin);
    }

    public GameUser addPlayer(Player p, GameUser.PlayerStatus playerStatus) {
        databaseController.getMoneydb().insertPlayer(p.getName());
        databaseController.getStatsdb().insertPlayer(p.getName());
        int money = databaseController.getMoneydb().getPlayer(p.getName());
        //plugin.getPlayerManager().changePlayerExp(p.getName(), 100);
        SQLUser user = databaseController.getPlayerdb().getPlayer(p);
        StatsUser statsUser = databaseController.getStatsdb().getPlayer(p);
        GameUser gameUser = new GameUser(user, statsUser, money, playerStatus);
        playersData.put(p.getName(), gameUser);
        return gameUser;
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
        ArenaStatus.initStatus(maxPlayers);
        ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());
        ArenaStatus.setStatus(ArenaStatus.Status.WAITING);
        ArenaStatus.setLore("§7§l> §a1.7 - 1.10");
        Random r = new Random();
        List<String> maps = Collections.singletonList("Infected1");
        String mapName = maps.get(r.nextInt(maps.size()));
        spawnsFile = new YmlFileHandler(plugin, Bukkit.getWorldContainer().getPath() + File.separator + mapName, "map.yml");
        spawnsFile.load();
        loadMap(mapName);
        changingPlayerEnabled = true;
    }

    public void reInit() {
        plugin.getArena().setMakingZombieEnabled(false);
        plugin.getCounter().cancel();
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    BungeeUtil.changeServer(plugin, p, "lobby1");
                }
            }
        }, 20 * 3);

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.unloadWorld(Bukkit.getWorlds().get(1), false);
                status = Status.WAITING;
                init();
            }
        }, 20 * 5);
    }

    public void preStartArena() {
        status = Status.STARTING;
        plugin.getCounter().start(startTime);
    }

    public void startArena() {
        status = Status.INGAME;
        ArenaStatus.setStatus(ArenaStatus.Status.INGAME);


        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.teleport(aliveIngameSpawn);
        }
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                //msg Nastaly zlowrogie ciemnosci blindness
                for (GameUser gameUser : playersData.values()) {
                    Player p = Bukkit.getPlayer(gameUser.getUsername());
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
                }
            }
        }, 20l * 5);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getArena().assignFirstZombie();
                plugin.getCounter().start(ingameTime);

            }
        }, 20l * 10);
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
        return getPlayersData().size() >= getMinPlayers();
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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getIngameTime() {
        return ingameTime;
    }

    public Location getZombieIngameSpawn() {
        return zombieIngameSpawn;
    }

    public Location getAliveIngameSpawn() {
        return aliveIngameSpawn;
    }

    public Status getStatus() {
        return status;
    }

    public void setZombieIngameSpawn(Location zombieIngameSpawn) {
        this.zombieIngameSpawn = zombieIngameSpawn;
    }

    public void setAliveIngameSpawn(Location aliveIngameSpawn) {
        this.aliveIngameSpawn = aliveIngameSpawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void saveLocationsToFile() {
        try {

            spawnsFile.
                    getConfig().
                    set("zombieIngameSpawn", LocationUtil.entityLocationToString(zombieIngameSpawn));
            spawnsFile.save();
        } catch (Exception ex) {

        }
        try {
            spawnsFile.getConfig().set("aliveIngameSpawn", LocationUtil.entityLocationToString(aliveIngameSpawn));
            spawnsFile.save();
        } catch (Exception ex) {

        }
        try {
            spawnsFile.getConfig().set("spawn", LocationUtil.entityLocationToString(spawn));
            spawnsFile.save();
        } catch (Exception ex) {

        }

    }

    public boolean isMakingZombieEnabled() {
        return changingPlayerEnabled;
    }

    public void setMakingZombieEnabled(boolean makingZombieEnabled) {
        this.changingPlayerEnabled = makingZombieEnabled;
    }

    public void loadMap(String mapName) {
        World w = Bukkit.createWorld(new WorldCreator(mapName));
        w.setMonsterSpawnLimit(1);
        w.setAnimalSpawnLimit(1);
        w.getEntities().clear();
        w.setGameRuleValue("doMobSpawning", "false");
        w.setAutoSave(false);
        w.setDifficulty(Difficulty.PEACEFUL);
        try {
            this.setAliveIngameSpawn(LocationUtil.entityStringToLocation(spawnsFile.getConfig().getString("aliveIngameSpawn")));
        } catch (Exception e) {
            System.out.print("Brak spawnow dla alive (i lub)");
        }
        try {
            this.setZombieIngameSpawn(LocationUtil.entityStringToLocation(spawnsFile.getConfig().getString("zombieIngameSpawn")));
        } catch (Exception e) {
            System.out.print("Brak spawnow dla  zombie (i lub)");
        }
        try {
            this.setSpawn(LocationUtil.entityStringToLocation(spawnsFile.getConfig().getString("spawn")));
        } catch (Exception e) {
            System.out.print("Brak spawnu generalnego (i lub)");
        }
    }

    public void makePlayerZombie(Player p, GameUser attackedUser) {
        String[] tab = new String[1];
        tab[0] = p.getName();
        attackedUser.changePlayerStatus(GameUser.PlayerStatus.ZOMBIE);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1), true);
        p.getInventory().setHelmet(new ItemStack(Material.PUMPKIN, 1));
        DisguiseAPI.disguiseIgnorePlayers(p, new MobDisguise(DisguiseType.ZOMBIE), tab);
        if (plugin.getArena().count(GameUser.PlayerStatus.ALIVE) == 0) {
            Bukkit.broadcastMessage("Zombie wygraly!");
        }
    }

    public void assignFirstZombie() {
        Random r = new Random();
        String[] tab = new String[1];

        List<GameUser> list = new ArrayList<>(this.getPlayersData().values());
        int chose = r.nextInt(list.size());
        GameUser chosen = list.get(chose);
        tab[0] = chosen.getUsername();
        StartKits kits = new StartKits();
        for (GameUser gameUser : list) {
            String message = getDatabaseController().
                    getMessagedb().
                    getMessage(gameUser.getLanguage(),
                            "infected.startmsg").
                    replace("%PLAYER%",
                            chosen.getUsername());
            Player p = Bukkit.getPlayer(gameUser.getUsername());
            p.sendMessage(message);
            if (gameUser.equals(chosen)) {
                gameUser.changePlayerStatus(GameUser.PlayerStatus.ZOMBIE);
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1), true);
                p.getInventory().setHelmet(new ItemStack(Material.PUMPKIN, 1));
                DisguiseAPI.disguiseIgnorePlayers(p, new MobDisguise(DisguiseType.ZOMBIE), tab);
                String choosenmsg = getDatabaseController().getMessagedb().getMessage(gameUser.getLanguage(), "infected.playerchoosen");
                p.sendMessage(choosenmsg);
                //p.teleport(zombieIngameSpawn);
                p.getWorld().strikeLightning(p.getLocation());
            } else {
                kits.giveAliveKit(p);
            }
            p.playSound(p.getLocation(), Sound.GHAST_SCREAM, r.nextInt(6), 1);
        }
        //Bukkit.broadcastMessage("Gracz " + chosen.getUsername() + " zostal zaatakowany przez tajemnicza istote i zakażony przez co stał się zombie ")

    }

    public void checkIfAliveWin() {
        if (plugin.getArena().count(GameUser.PlayerStatus.ALIVE) > 0) {
            for (GameUser gameuser : playersData.values()) {
                if (gameuser.isAlive()) {
                    plugin.getArena().getDatabaseController().getMoneydb().changePlayerMoney(gameuser.getUsername(), 20);
                }
                Player p = Bukkit.getPlayer(gameuser.getUsername());
                String aliveWinMsg = databaseController.getMessagedb().getMessage(gameuser.getLanguage(), "infected.alivewin");
                p.sendMessage(aliveWinMsg);
            }
            plugin.getArena().reInit();
        }
    }

    public void checkIfZombieWin() {
        if (plugin.getArena().count(GameUser.PlayerStatus.ALIVE) <= 0) {
            for (GameUser gameuser : playersData.values()) {
                Player p = Bukkit.getPlayer(gameuser.getUsername());
                String zombieWinMsg = databaseController.getMessagedb().getMessage(gameuser.getLanguage(), "infected.zombiewin");
                p.sendMessage(zombieWinMsg);
            }
            plugin.getArena().reInit();
        }
    }

    public HashMap<String, Long> getDelayedKnockback() {
        return delayedKnockback;
    }


}
