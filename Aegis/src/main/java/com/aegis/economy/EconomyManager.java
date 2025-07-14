package com.aegis.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Basic in-memory economy with configurable starting balance.
 */
public class EconomyManager implements Listener {
    private final Map<UUID, Double> balances = new HashMap<>();
    private final double startingBalance;

    public EconomyManager(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), startingBalance);
    }

    public void deposit(Player player, double amount) {
        balances.put(player.getUniqueId(), getBalance(player) + amount);
    }

    public boolean withdraw(Player player, double amount) {
        double balance = getBalance(player);
        if (balance < amount) return false;
        balances.put(player.getUniqueId(), balance - amount);
        return true;
    }

    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "Economía");

        ItemStack bal = new ItemStack(org.bukkit.Material.PAPER);
        ItemMeta balMeta = bal.getItemMeta();
        balMeta.setDisplayName("Balance: " + getBalance(player));
        bal.setItemMeta(balMeta);
        inv.setItem(4, bal);

        ItemStack dep = new ItemStack(org.bukkit.Material.EMERALD);
        ItemMeta depMeta = dep.getItemMeta();
        depMeta.setDisplayName("Depositar 100");
        dep.setItemMeta(depMeta);
        inv.setItem(2, dep);

        ItemStack wit = new ItemStack(org.bukkit.Material.REDSTONE);
        ItemMeta witMeta = wit.getItemMeta();
        witMeta.setDisplayName("Retirar 100");
        wit.setItemMeta(witMeta);
        inv.setItem(6, wit);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getView().getTitle().equals("Economía")) return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot == 2) {
            deposit(player, 100);
            player.sendMessage("Depositaste 100");
            openMenu(player);
        } else if (slot == 6) {
            if (withdraw(player, 100)) {
                player.sendMessage("Retiraste 100");
            } else {
                player.sendMessage("Fondos insuficientes.");
            }
            openMenu(player);
        }
    }
}
