package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Arena;
import com.gmail.grzegorz2047.infected.GameUser;
import com.gmail.grzegorz2047.infected.Infected;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Created by grzeg on 29.10.2016.
 */
public class EntityDamageByEntityListener implements Listener {


    private final Infected plugin;

    public EntityDamageByEntityListener(Infected plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Arena arena = plugin.getArena();
        Entity damager = e.getDamager();
        Entity defender = e.getEntity();
        if (damager instanceof Player && defender instanceof Player) {
            Player attacker = (Player) damager;
            Player attacked = (Player) defender;
            GameUser attackedUser = arena.getPlayer(attacked.getName());
            GameUser attackerUser = arena.getPlayer(attacker.getName());
            if (attackerUser.isZombie() && attackedUser.isAlive()) {
                if (!arena.isMakingZombieEnabled()) {
                    String cantinfectMsg = arena.getDatabaseController().getMessagedb().getMessage(attackedUser.getLanguage(), "infected.cantinfect");
                    attacker.sendMessage(cantinfectMsg);
                    return;
                }
                plugin.getArena().getDatabaseController().getMoneydb().changePlayerMoney(attacker.getName(), 1);
                String infectedMsg = arena.getDatabaseController().getMessagedb().getMessage(attackedUser.getLanguage(), "infected.nowinfected");
                arena.makePlayerZombie(attacked, attackedUser);
                attacked.sendMessage(infectedMsg);
                plugin.getArena().getDatabaseController().getStatsdb().increaseValueBy(attackerUser.getUsername(), "kills", 1);

            } else if (attackerUser.isAlive() && attackedUser.isZombie()) {
                e.setDamage(0);
            } else {
                e.setCancelled(true);
            }
        } else if (e.getDamager() instanceof Egg) {
            if (e.getEntity() instanceof Player) {
                Player attacked = (Player) e.getEntity();
                ProjectileSource attackerEntity = ((Egg) e.getDamager()).getShooter();
                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;
                    GameUser attackedUser = arena.getPlayer(attacked.getName());
                    GameUser attackerUser = arena.getPlayer(attacker.getName());
                    if (attackedUser.isZombie()) {
                        attacked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1));
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                p.teleport(plugin.getArena().getAliveIngameSpawn());

            }
        } else if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            e.setCancelled(true);
        }


    }

}
