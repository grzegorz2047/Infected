package com.gmail.grzegorz2047.infected.listeners;

import com.gmail.grzegorz2047.infected.Arena;
import com.gmail.grzegorz2047.infected.GameUser;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Created by grzeg on 29.10.2016.
 */
public class EntityDamageByEntityListener implements Listener {

    private final Arena arena;

    public EntityDamageByEntityListener(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity defender = e.getEntity();
        if (e.getDamager() instanceof Egg) {
            if (e.getEntity() instanceof Player) {
                Player attacked = (Player) e.getEntity();
                ProjectileSource attackerEntity = ((Egg) e.getDamager()).getShooter();
                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;
                    GameUser attackedUser = arena.getPlayer(attacked.getName());
                    GameUser attackerUser = arena.getPlayer(attacker.getName());
                    if (attackedUser.isZombie()) {
                        attacked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 1));
                    }
                }
            }
        }
    }

}
