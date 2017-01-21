package com.gmail.grzegorz2047.infected.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by grzeg on 30.10.2016.
 */
public class StartKits {
    public StartKits() {

    }

    public static void giveAliveKit(Player p) {
        ItemStack magicStick = new ItemStack(Material.STICK, 1);
        magicStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        p.getInventory().setItem(0, magicStick);
        p.getInventory().addItem(new ItemStack(Material.EGG, 5));
        p.getInventory().addItem(new ItemStack(Material.GLOWSTONE, 64));
        p.getInventory().addItem(new ItemStack(Material.PUMPKIN, 64));
        p.getInventory().addItem(new ItemStack(Material.WOOD, 64));
        p.getInventory().addItem(new ItemStack(Material.WORKBENCH, 1));
        p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 2));
        p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 2));

    }

    public static void giveZombieKit(Player p) {
        p.getInventory().addItem(new ItemStack(Material.WOOL, 64));
        p.getInventory().addItem(new ItemStack(Material.SPONGE, 64));
        p.getInventory().addItem(new ItemStack(Material.WOOD, 64));
        p.getInventory().addItem(new ItemStack(Material.GLASS, 64));
        p.getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
    }
}
