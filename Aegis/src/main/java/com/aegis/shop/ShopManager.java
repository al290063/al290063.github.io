package com.aegis.shop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopManager {
    public void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Aegis Shop");
        // TODO: agregar ítems a la tienda
        player.openInventory(inv);
    }
}
