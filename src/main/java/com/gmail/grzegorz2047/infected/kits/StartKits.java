package com.gmail.grzegorz2047.infected.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by grzeg on 30.10.2016.
 */
public class StartKits {
    public StartKits() {

    }

    public void giveAliveKit(Player p) {
        ItemStack magicStick = new ItemStack(Material.STICK, 1);
        magicStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        p.getInventory().setItem(0,magicStick);
    }
}
