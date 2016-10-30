package com.gmail.grzegorz2047.infected.scoreboard;

import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.grzegorz2047.databaseapi.SQLUser;

/**
 * Created by s416045 on 2016-04-19.
 */
public class ScoreboardAPI {

    private final Infected plugin;
    private Scoreboard scoreboard;

    public ScoreboardAPI(Infected plugin) {
        this.plugin = plugin;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        if (scoreboard.getObjective("sidebar") == null) {
            Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§6§l Punkty kontrolne");
        }


    }

    public void createScoreboard(Player p, GameUser user) {
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Objective objective = p.getScoreboard().registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

//        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        //      tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        //addTabListEntry(scoreboard, p.getName(), " §7"); //color tablist?

        //addEntry(scoreboard, objective, team1Label, "0", 8);
        //addEntry(scoreboard, objective, team2Label, "0", 7);
        //addEntry(scoreboard, objective, team3Label, "0", 6);
        //addEntry(scoreboard, objective, team4Label, "0", 5);
        addEntry(scoreboard, objective, "§   ", "", 15);
        //addEntry(scoreboard, objective, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.scoreboard.kills"), String.valueOf(0), 3);
        //addEntry(scoreboard, objective, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.scoreboard.money"), String.valueOf(user.getMoney()), 2);
        //addEntry(scoreboard, objective, "§bTEAM2§6", "0", 3);
        //addEntry(scoreboard, objective, "§cTEAM3§6", "0", 2);
        //addEntry(scoreboard, objective, "§eTEAM4§6", "0", 2);
        // addEntry(scoreboard, objective, "§§", "", 9);
        addEntry(scoreboard, objective, "§    ", "", 14);
        /*Team t1 = scoreboard.registerNewTeam("team1");
        t1.setPrefix("§a");
        Team t2 = scoreboard.registerNewTeam("team2");
        t2.setPrefix("§b");
        Team t3 = scoreboard.registerNewTeam("team3");
        t3.setPrefix("§c");
        Team t4 = scoreboard.registerNewTeam("team4");
        t4.setPrefix("§e");
        */
        p.setScoreboard(scoreboard);
    }

    public void refreshTags() {/*
        Scoreboard sc = p.getScoreboard();
        Team t1 = sc.getTeam("team1");
        t1.setPrefix("§a");
        Team t2 = sc.getTeam("team2");
        t2.setPrefix("§b");
        Team t3 = sc.getTeam("team3");
        t3.setPrefix("§c");
        Team t4 = sc.getTeam("team4");
        t4.setPrefix("§e");*/
        for (GameUser user : plugin.getArena().getPlayersData().values()) {
            Player p = Bukkit.getPlayer(user.getUsername());
            Scoreboard sc = p.getScoreboard();
            updateEntry(sc, "alive", plugin.getArena().count(GameUser.PlayerStatus.ALIVE));
            updateEntry(sc, "zombie", plugin.getArena().count(GameUser.PlayerStatus.ZOMBIE));
        }
    }


    public Team addEntry(Scoreboard scoreboard, Objective objective, String name, String value, int position) {
        Team t = scoreboard.registerNewTeam(name);
        t.setPrefix(" §4❤  §7");
        //t.setPrefix(" §0∙ ");
        t.addEntry(name);
        t.setSuffix(" §2" + value);
        Score score = objective.getScore(name);
        score.setScore(position);
        return t;
    }

    public Team addTabListEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.registerNewTeam(name);
        t.setPrefix(value);
        //t.setPrefix(" §0∙ ");
        t.addEntry(name);
        t.setSuffix(" ");
        return t;
    }

    public void updateTabListEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.getTeam(name);
        t.setPrefix(value);
    }

    public void updateEntry(Scoreboard scoreboard, String name, int value) {
        Team t = scoreboard.getTeam(name);
        t.setSuffix(" §2" + value);
    }

    public void updateIncreaseEntry(Scoreboard scoreboard, String name, int value) {
        Team t = scoreboard.getTeam(name);
        int val = Integer.parseInt(t.getSuffix());
        t.setSuffix(String.valueOf(val + value));
    }

    public void refreshIngameScoreboard(int team1, int team2, int team3, int team4) {/*
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = p.getScoreboard();
            updateIncreaseEntry(scoreboard, this.team1Label, team1);
            updateIncreaseEntry(scoreboard, this.team2Label, team2);
            updateIncreaseEntry(scoreboard, this.team3Label, team3);
            updateIncreaseEntry(scoreboard, this.team4Label, team4);
        }*/
    }

    private void updateEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.getTeam(name);
        t.setSuffix(" " + value);
    }

    public void removeEntry(Scoreboard scoreboard, String name) {
        Team t = scoreboard.getTeam(name);
        t.unregister();
    }

    public void colorTabListPlayer(Scoreboard scoreboard, String name, String color) {
        updateTabListEntry(scoreboard, name, color);
    }

    public void updateDisplayName(int time, Player p) {
        Scoreboard scoreboard = p.getScoreboard();
        scoreboard.getObjective(
                DisplaySlot.SIDEBAR).
                setDisplayName(
                        "§a" + formatIntoHHMMSS(time) +
                                " §6Infected #" +
                                (Bukkit.getPort() % 200) +
                                " §a" + plugin.getArena().countAll() +
                                "/" + plugin.getArena().getMaxPlayers());
    }

    static String formatIntoHHMMSS(int secsIn) {

        int remainder = secsIn % 3600,
                minutes = remainder / 60,
                seconds = remainder % 60;

        return ((minutes < 10 ? "0" : "") + minutes
                + ":" + (seconds < 10 ? "0" : "") + seconds);

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
