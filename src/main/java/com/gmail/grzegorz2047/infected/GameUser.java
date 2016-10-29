package com.gmail.grzegorz2047.infected;


import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.StatsUser;

import java.util.HashMap;

/**
 * Created by grzeg on 16.05.2016.
 */
public class GameUser extends SQLUser {

    private int money;
    private StatsUser statsUser;

    private enum PlayerStatus {ALIVE, ZOMBIE, SPECTATOR}

    private PlayerStatus playerStatus;
    // private List<Transaction> transactions;
    /// private List<Material> boughtTempItems = new ArrayList<Material>();

    public GameUser(int userid, String username, String language, String lastip, int exp, boolean pets, boolean effects, boolean disguise, String rank, long rankto) {
        super(userid, username, language, lastip, exp, pets, effects, disguise, rank, rankto);
    }

    public GameUser(SQLUser user, StatsUser statsUser, /*List<Transaction> transactions,*/ int money, PlayerStatus playerStatus) {
        super(user.getUserid(), user.getUsername(), user.getLanguage(),
                user.getLastip(), user.getExp(), user.hasPets(), user.hasEffects(), user.hasDisguise(), user.getRank(), user.getRankto());
        this.money = money;
        this.statsUser = statsUser;
        this.playerStatus = playerStatus;
        //this.transactions = transactions;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int change) {
        this.money += change;
    }


 /*
    public int getIngameKills() {
        return ingameKills;
    }

    public void setIngameKills(int ingameKills) {
        this.ingameKills = ingameKills;
    }

    public void increaseIngameKills(int ingameKills) {
        this.ingameKills += ingameKills;
    }*/

    public StatsUser getStatsUser() {
        return statsUser;
    }
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
    public void changePlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }
/*
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Material> getBoughtTempItems() {
        return boughtTempItems;
    }

    public int getProtectedFurnaces() {
        return protectedFurnaces;
    }

    public void setProtectedFurnaces(int protectedFurnaces) {
        this.protectedFurnaces = protectedFurnaces;
    }
}
*/
}